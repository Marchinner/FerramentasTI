package entities;

public class User {

    private String username;
    private String completeName;
    private String mail;
    private String organizationalUnit;
    private String logonWorkstations;
    private String profilePath;
    private String whenCreated;
    private int organizationalUnitIndex;
    private String enabled;

    public User(String username, String completeName, String mail, String organizationalUnit,
                String logonWorkstations, String profilePath, String whenCreated, String enabled) {
        this.username = username;
        this.completeName = completeName;
        this.mail = mail;
        this.organizationalUnit = organizationalUnit;
        this.logonWorkstations = logonWorkstations;
        this.profilePath = profilePath;
        this.whenCreated = whenCreated;
        this.enabled = enabled;
        setOUIndex();
    }

    public String getCompleteName() {
        return completeName;
    }

    public String getMail() {
        return mail;
    }

    public String getLogonWorkstation() {
        return logonWorkstations;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getWhenCreated() {
        return whenCreated;
    }

    public String getEnabled() {
        return enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setEnabled() {
        enabled = "True";
    }

    public void setDisabled() {
        enabled = "False";
    }

    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public int getOrganizationalUnitIndex() {
        return organizationalUnitIndex;
    }

    public void setOrganizationalUnitIndex(int index) {
        this.organizationalUnitIndex = index;
    }

    private void setOUIndex() {
        switch (organizationalUnit) {
            case "ADMINISTRAÇÃO" -> organizationalUnitIndex = 0;
            case "ALMOXARIFADO" -> organizationalUnitIndex = 1;
            case "COMERCIAL" -> organizationalUnitIndex = 2;
            case "COMPRAS" -> organizationalUnitIndex = 3;
            case "CONTROLADORIA" -> organizationalUnitIndex = 4;
            case "DIRETORIA" -> organizationalUnitIndex = 5;
            case "ECOMMERCE" -> organizationalUnitIndex = 6;
            case "EXPEDICAO" -> organizationalUnitIndex = 7;
            case "FICHA TECNICA" -> organizationalUnitIndex = 8;
            case "FINANCEIRO" -> organizationalUnitIndex = 9;
            case "INSTITUTO-ALLAMO" -> organizationalUnitIndex = 10;
            case "INFORMATICA" -> organizationalUnitIndex = 11;
            case "CONTAS TEMPORÁRIAS" -> organizationalUnitIndex = 12;
            case "LOGISTICA" -> organizationalUnitIndex = 13;
            case "MANUTENCAO" -> organizationalUnitIndex = 14;
            case "MARKETING" -> organizationalUnitIndex = 15;
            case "MODELAGEM" -> organizationalUnitIndex = 16;
            case "PCP" -> organizationalUnitIndex = 17;
            case "PRODUCAO" -> organizationalUnitIndex = 18;
            case "QUALIDADE" -> organizationalUnitIndex = 19;
            case "RECEPCAO" -> organizationalUnitIndex = 20;
            case "RH" -> organizationalUnitIndex = 21;
            case "INATIVAS - CONTAS" -> organizationalUnitIndex = 22;
            default -> organizationalUnitIndex = 0;
        }
    }

    public void setLogonWorkstation(String logonWorkstations) {
        this.logonWorkstations = logonWorkstations;
    }

    public void setMail(String emailAddress) {
        mail = emailAddress;
    }
}
