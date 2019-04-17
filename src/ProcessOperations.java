import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ProcessOperations {

    private static HashMap<Integer, ProcessInfo> runningProcessesMap;
    private static HashMap<Integer, ProcessInfo> monitoringProcesses;

    public String generateProcessList(){

        this.updateRunningProcessList();
        String text = "=============Processes that are running on the system=============\n";
        text += "=============Scroll down to see the complete list or to go back=============\n\n";

        for(ProcessInfo aProcess : runningProcessesMap.values()){
            text += "Process Name: " + aProcess.getImageName() + "\nPID: " + aProcess.getPid() + "\n\n";
        }
        text += "=============End of list=============";

        return text;
    }

    public boolean addProcessToMonitor(int pid){

        this.updateRunningProcessList();

        if(this.runningProcessesMap != null && !this.runningProcessesMap.containsKey(pid)){
            return false;
        }

        String imageName = this.getRunningProcessesMap().get(pid).getImageName();
        String sessionName = this.getRunningProcessesMap().get(pid).getSessionName();
        int sessionNum = this.getRunningProcessesMap().get(pid).getSessionNum();
        int memUsage = this.getRunningProcessesMap().get(pid).getMemUsage();

        if(this.monitoringProcesses == null){
            this.monitoringProcesses = new HashMap<>();
        }
        this.monitoringProcesses.put(pid, new ProcessInfo(imageName, pid, sessionName, sessionNum, memUsage));

        return true;
    }

    public boolean removeProcessToMonitor(int pid){

        if(this.monitoringProcesses != null && this.monitoringProcesses.containsKey(pid)){
            this.monitoringProcesses.remove(pid);
            return true;
        }
        return false;
    }

    private synchronized void updateRunningProcessList(){

        this.runningProcessesMap = new HashMap<>();
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

            this.runningProcessesMap.put(pid, new ProcessInfo(imageName, pid, sessionName, sessionNum, memUsage));
        }
        reader.close();
    }

    public static HashMap<Integer, ProcessInfo> getRunningProcessesMap() {
        return runningProcessesMap;
    }

    public static HashMap<Integer, ProcessInfo> getMonitoringProcesses() {
        return monitoringProcesses;
    }
}
