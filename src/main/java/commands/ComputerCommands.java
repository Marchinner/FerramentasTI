package commands;

public class ComputerCommands {

    public static String GetComputerStatus(String netbios) {
        return "Get-ADComputer -Identity " + netbios + " -Property * | Select Enabled | Format-Table -HideTableHeaders";
    }

    public static String GetComputerOperatingSystem(String netbios) {
        return "Get-ADComputer -Identity " + netbios + " -Property * | Select OperatingSystem | Format-Table " +
                "-HideTableHeaders";
    }

    public static String GetComputerLastIP(String netbios) {
        return "Get-ADComputer -Identity " + netbios + " -Property * | Select IPv4Address | Format-Table " +
                "-HideTableHeaders";
    }

    public static String GetComputerLastBoot(String netbios) {
        return "Get-ADComputer -Identity " + netbios + " -Property * | Select LastLogonDate | Format-Table " +
                "-HideTableHeaders";
    }

    public static String DisableComputer(String netbios) {
        return "Set-ADComputer -Identity " + netbios + " -Enabled $false";
    }

    public static String EnableComputer(String netbios) {
        return "Set-ADComputer -Identity " + netbios + " -Enabled $true";
    }

    public static String MoveComputer(String netbios, String status) {
        if (status.equals("True")) {
            return "Get-ADComputer -Identity " + netbios + " | Move-ADObject -TargetPath 'OU=WORKSTATIONS," +
                    "OU=STATIONS,OU=DOK,DC=dok0,DC=local'";
        } else {
            return "Get-ADComputer -Identity " + netbios + " | Move-ADObject -TargetPath 'OU=INATIVAS - WORKSTATION," +
                    "DC=dok0,DC=local'";
        }
    }

}
