import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProcessOperations {

    private static HashMap<String, ProcessInfo> processMap;

    private synchronized void updateRunningProcessList(){

        this.processMap = new HashMap<>();
        ArrayList<String> lines = new ArrayList<>();
        Process runningProcesses;
        Scanner reader;
        String imageName;
        int pid;
        String sessionName;
        int sessionNum;
        int memUsage;

        try{
            runningProcesses = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\tasklist.exe");
            reader = new Scanner(new InputStreamReader(runningProcesses.getInputStream()));
        }
        catch(IOException e){
            System.out.println();
            return;
        }

        while(reader.hasNextLine()){
            String temp = reader.nextLine();
            if(temp.length() != 0 && temp.charAt(0) == '='){
                break;
            }
        }
        int totalNum = 0;
        for(int i = 0, indexOf; reader.hasNextLine(); i++){
            lines.add(reader.nextLine());

            indexOf = lines.get(i).indexOf("  ");
            imageName = lines.get(i).substring(0, indexOf);
            lines.set(i, lines.get(i).substring(indexOf).trim());

            indexOf = lines.get(i).indexOf(" ");
            pid = Integer.parseInt(lines.get(i).substring(0, indexOf));
            lines.set(i, lines.get(i).substring(indexOf).trim());

            indexOf = lines.get(i).indexOf("  ");
            sessionName = lines.get(i).substring(0, indexOf);
            lines.set(i, lines.get(i).substring(indexOf).trim());

            indexOf = lines.get(i).indexOf("  ");
            sessionNum = Integer.parseInt(lines.get(i).substring(0, indexOf));
            lines.set(i, lines.get(i).substring(indexOf).trim());

            indexOf = lines.get(i).indexOf(" ");
            memUsage = Integer.parseInt(lines.get(i).substring(0, indexOf).replace(",", ""));
            lines.set(i, lines.get(i).substring(indexOf).trim());

            this.processMap.put(imageName, new ProcessInfo(imageName, pid, sessionName, sessionNum, memUsage));
            totalNum++;
        }
        System.out.println(totalNum);
        reader.close();
    }

    public String generateProcessList(){

        this.updateRunningProcessList();
        String text = "=============Running System ProcessMonitorApp=============\n";

        for(ProcessInfo entry : processMap.values()){
            text += "Process Name: " + entry.getImageName() + "\nPID: " + entry.getPid() + "\n\n";
        }
        text += "=============End of list=============";

        return text;
    }

    public HashMap<String, ProcessInfo> getProcessMap() {
        return this.processMap;
    }
}
