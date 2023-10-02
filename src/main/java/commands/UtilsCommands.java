package commands;

public class UtilsCommands {

    public static String GetUsersWithoutProfilePath() {
        return "Get-ADUser -Filter 'enabled -eq $true -and profilepath -notlike \"*\"' -Property SamAccountName, " +
                "ProfilePath | Select SamAccountName | FT -HideTableHeaders";
    }

    public static String GetUsersWithWrongProfilePath() {
        return "Get-ADUser -Filter 'enabled -eq $true -and profilepath -notlike \"*dok-fs01-bgu*\"' " +
                "-Property SamAccountName, ProfilePath | Select SamAccountName | FT -HideTableHeaders";
    }

    public static String GetUsersWithoutLogonWorkstations() {
        return "Get-ADUser -Filter 'enabled -eq $true -and logonWorkstations -notlike \"*\"' -SearchBase \"OU=DOK," +
                "DC=dok0,DC=local\" -Property SamAccountName | Select SamAccountName | Format-Table -HideTableHeaders";
    }

    public static String GetInactiveUsersOutsideInactiveOU() {
        return "Get-ADUser -Filter 'Enabled -eq $false' -SearchBase \"OU=DOK,DC=dok0,DC=local\" | " +
                "Select SamAccountName, DistinguishedName | FT -HideTableHeaders";
    }

    public static String GetActiveUsersInInactiveOU() {
        return "Get-ADUser -Filter 'Enabled -eq $true' -SearchBase \"OU=INATIVAS - CONTAS,DC=dok0,DC=local\" | " +
                "Select SamAccountName, DistinguishedName | FT -HideTableHeaders";
    }

}
