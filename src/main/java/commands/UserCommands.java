package commands;


public class UserCommands {


    /***
     * PowerShell command to returns a user's complete name
     * @param username user's username
     * @return the correct command to returns a user's complete name
     */
    public static String GetUserCompleteName(String username) {
        return "Get-ADUser -Identity " + username + " | Select Name | Format-Table -HideTableHeaders";
    }

    /***
     * PowerShell command to returns a user's email
     * @param username user's username
     * @return the correct command to returns a user's email
     */
    public static String GetUserEmailAddress(String username) {
        return "Get-ADUser -Identity " + username + " -Property * | Select Mail | Format-Table -HideTableHeaders";
    }

    public static String GetUserOrganizationalUnit(String username) {
        return "Get-ADUser -Identity " + username + " -Property * | Select DistinguishedName | Format-Table -HideTableHeaders";
    }

    public static String GetUserLogonWorkstations(String username) {
        return "Get-ADUser -Identity " + username + " -Property * | Select LogonWorkstations | Format-Table -HideTableHeaders";
    }

    public static String GetUserProfilePath(String username) {
        return "Get-ADUser -Identity " + username + " -Property * | Select ProfilePath | Format-Table -HideTableHeaders";
    }

    public static String GetUserWhenCreated(String username) {
        return "Get-ADUser -Identity " + username + " -Property * | Select WhenCreated | Format-Table -HideTableHeaders";
    }

    /***
     * PowerShell command to return if a user is enabled or not
     * @param username user's username
     * @return command to return if a user is enabled or not
     */
    public static String GetUserIsEnabled(String username) {
        return "Get-ADUser -Identity " + username + " -Property * | Select Enabled | Format-Table -HideTableHeaders";
    }

    public static String EnableUser(String username) {
        return "Enable-ADAccount -Identity " + username;
    }

    public static String DisableUser(String username) {
        return "Disable-ADAccount -Identity " + username;
    }

    public static String TransferUser(String username, int organizationalUnitIndex) {
        String command = "Get-ADUser -Identity " + username + " | Move-ADObject -TargetPath ";
        switch (organizationalUnitIndex) {
            case 0 -> command = command + "'OU=ADMINISTRAÇÃO,OU='";
            case 1 -> command = command + "'OU=ALMOXARIFADO,OU='";
            case 2 -> command = command + "'OU=COMERCIAL,OU='";
            case 3 -> command = command + "'OU=COMPRAS,OU='";
            case 4 -> command = command + "'OU=CONTROLADORIA,OU='";
            case 5 -> command = command + "'OU=DIRETORIA,OU='";
            case 6 -> command = command + "'OU=ECOMMERCE,OU='";
            case 7 -> command = command + "'OU=EXPEDICAO,OU='";
            case 8 -> command = command + "'OU=FICHA TECNICA,OU='";
            case 9 -> command = command + "'OU=FINANCEIRO,OU='";
            case 10 -> command = command + "'OU=INSTITUTO-ALLAMO,OU='";
            case 11 -> command = command + "'OU=INFORMATICA,OU='";
            case 12 -> command = command + "'OU=CONTAS TEMPORÁRIAS,OU='";
            case 13 -> command = command + "'OU=LOGISTICA,OU='";
            case 14 -> command = command + "'OU=MANUTENCAO,OU='";
            case 15 -> command = command + "'OU=MARKETING,OU='";
            case 16 -> command = command + "'OU=MODELAGEM,OU='";
            case 17 -> command = command + "'OU=PCP,OU='";
            case 18 -> command = command + "'OU=PRODUCAO,OU='";
            case 19 -> command = command + "'OU=QUALIDADE,OU='";
            case 20 -> command = command + "'OU=RECEPCAO,OU='";
            case 21 -> command = command + "'OU=RH,OU='";
            case 22 -> command = command + "'OU='";
        }

        return command;
    }

    public static String ChangeUserLogonWorkstations(String username, String logonWorkstation) {
        return "Set-ADUser -Identity " + username + " -LogonWorkstations " + logonWorkstation;
    }

    public static String SetUserEmailAddress(String username, String newEmailAddress) {
        return "Set-ADUser -Identity " + username + " -EmailAddress \"" + newEmailAddress + "\"";
    }
}
