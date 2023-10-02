package entities;

public class Computer {

    private String netbios;
    private String enabled;
    private String lastIP;
    private String lastLogonDate;
    private String operatingSystem;

    public Computer(String netbios, String enabled, String lastIP, String lastLogonDate, String operatingSystem) {
        this.netbios = netbios;
        this.enabled = enabled;
        this.lastIP = lastIP;
        this.lastLogonDate = lastLogonDate;
        this.operatingSystem = operatingSystem;
    }

    public String getNetbios() {
        return netbios;
    }

    public void setNetbios(String netbios) {
        this.netbios = netbios;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getLastIP() {
        return lastIP;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public String getLastLogonDate() {
        return lastLogonDate;
    }

    public void setLastLogonDate(String lastLogonDate) {
        this.lastLogonDate = lastLogonDate;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}
