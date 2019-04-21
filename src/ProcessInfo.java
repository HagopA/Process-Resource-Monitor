public class ProcessInfo {

    private String imageName;
    private int pid;
    private String sessionName;
    private int sessionNum;
    private int memUsage;
    private int percentCpuUsage;

    public ProcessInfo(String imageName, int pid, String sessionName, int sessionNum, int memUsage) {
        this.imageName = imageName;
        this.pid = pid;
        this.sessionName = sessionName;
        this.sessionNum = sessionNum;
        this.memUsage = memUsage;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public int getSessionNum() {
        return sessionNum;
    }

    public void setSessionNum(int sessionNum) {
        this.sessionNum = sessionNum;
    }

    public int getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(int memUsage) {
        this.memUsage = memUsage;
    }

    public int getPercentCpuUsage() {
        return percentCpuUsage;
    }

    public void setPercentCpuUsage(int percentCpuUsage) {
        this.percentCpuUsage = percentCpuUsage;
    }
}
