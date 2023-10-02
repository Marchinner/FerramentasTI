package main;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import entities.Computer;
import entities.User;
import utilz.Email;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;

import static commands.UserCommands.*;
import static commands.ComputerCommands.*;
import static commands.UtilsCommands.*;

public class ApplicationWindow extends JFrame {
    private ImageIcon icon = null;
    private JPanel applicationPanel;
    private JTabbedPane tabbedPane;
    private JPanel userTabPanel;
    private JTextField textField_username;
    private JButton button_searchUser;
    private JButton button_toggleUserStatus;
    private JComboBox comboBox_organizationalUnits;
    private JButton button_transferUser;
    private JLabel label_userCompleteName;
    private JLabel label_userStatus;
    private JLabel label_userProfilePath;
    private JLabel label_userWhenCreated;
    private JTextField textField_userLogonWorkstations;
    private JButton button_changeUserLogonWorkstations;
    private JPanel utilsTabPanel;
    private JTextArea textArea_actionResult;
    private JComboBox comboBox_Action;
    private JButton button_loadAction;
    private JList list1;
    private JTextField textField_userEmail;
    private JButton button_changeUserEmailAddress;
    private JTextField textField_computerNetbios;
    private JButton button_searchComputer;
    private JButton button_toggleComputerStatus;
    private JLabel label_computerStatus;
    private JLabel label_computerOperatingSystem;
    private JLabel label_computerLastIP;
    private JLabel label_computerLastBoot;
    private JRadioButton radioButton_movePatrimonyItem;
    private JRadioButton radioButton_newItem;
    private JTextField textField_move_itemNumber;
    private JTextField textField_move_itemActualPlace;
    private JTextField textField_move_itemNewPlace;
    private JTextField textField_move_itemDescription;
    private JTextField textField_patrimonial_emailTo;
    private JPasswordField passwordField_patrimoniel_senderPassword;
    private JButton button_patrimonial_sendEmail;
    private JTextField textField_newItemNumber;
    private JTextField textField_newItemPlace;
    private JTextField textField_newItemDescription;
    private JButton button_attachPlaquetaPhoto;
    private JButton button_attachFrontalPhoto;
    private JButton button_attachTraseiraPhoto;
    private JLabel label_plaqueta;
    private JLabel label_frontal;
    private JLabel label_traseira;
    private JRadioButton radioButton_NewUserTab_sexMale;
    private JRadioButton radioButton_NewUserTab_sexFemale;
    private JTextField textField_NewUserTab_username;
    private JTextField textField_NewUserTab_userPassword;
    private JTextField textField_NewUserTab_userPrinters;
    private JTextField textField_NewUserTab_PrintersPin;
    private JRadioButton radioButton_NewUserTab_jrtiYes;
    private JRadioButton radioButton_NewUserTab_jrtiNo;
    private JTextField textField_NewUserTab_jrtiUsername;
    private JTextField textField_NewUserTab_jrtiUsernamePassword;
    private JRadioButton radioButton_NewUserTab_sysempYes;
    private JRadioButton radioButton_NewUserTab_sysempNo;
    private JTextField textField_NewUserTab_sysempUsername;
    private JTextField textField_NewUserTab_sysempUsernamePassword;
    private JTextField textField_NewUserTab_userEmail;
    private JButton button_NewUserTab_sendEmail;
    private JPasswordField passwordField_NewUserTab_senderPassword;
    private JLabel label_actionResult;
    private PowerShell powerShell = PowerShell.openSession();
    private User user;
    private Computer computer;
    private Image enabledIcon = ImageIO.read(getClass().getResource("/enabled.png"));
    private Image disabledIcon = ImageIO.read(getClass().getResource("/disabled.png"));
    private String plaquetaPhotoPath;
    private String frontalPhotoPath;
    private String traseiraPhotoPath;

    public ApplicationWindow() throws IOException {
        try {
            java.net.URL iconUrl = ApplicationWindow.class.getResource("/logo.png");
            setTitle("Gerenciador AD");
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setFocusable(true);
            add(applicationPanel);
            pack();
            setLocationRelativeTo(null);
            if (iconUrl != null) {
                icon = new ImageIcon(iconUrl);
                setIconImage(icon.getImage());
            } else {
                System.out.println("Image not found");
            }
            setVisible(true);

            textField_username.grabFocus();

            label_plaqueta.setIcon(new ImageIcon(disabledIcon));
            label_frontal.setIcon(new ImageIcon(disabledIcon));
            label_traseira.setIcon(new ImageIcon(disabledIcon));

            button_searchUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = textField_username.getText();
                    if (userExists(username)) {
                        user = new User(
                                username,
                                executeCommand(GetUserCompleteName(username)),
                                executeCommand(GetUserEmailAddress(username)),
                                formatOrganizacionalUnit(executeCommand(GetUserOrganizationalUnit(username))),
                                executeCommand(GetUserLogonWorkstations(username)),
                                executeCommand(GetUserProfilePath(username)),
                                executeCommand(GetUserWhenCreated(username)),
                                executeCommand(GetUserIsEnabled(username))
                        );
                        enableAllUserButtons();
                        comboBox_organizationalUnits.setEnabled(true);
                        loadUserInformation(user);
                    } else {
                        disableAllUserButtons();
                        cleanTextInputField(textField_username);
                        comboBox_organizationalUnits.setEnabled(false);
                        resetAllInformation();
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado!", "ERRO!",
                                JOptionPane.ERROR_MESSAGE);
                    }


                }
            });
            button_toggleUserStatus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (user.getEnabled().contains("False")) {
                        executeCommand(EnableUser(user.getUsername()));
                        user.setEnabled();
                        JOptionPane.showMessageDialog(null, "Usuário ATIVADO!");
                        loadUserInformation(user);
                    } else {
                        executeCommand(DisableUser(user.getUsername()));
                        user.setDisabled();
                        JOptionPane.showMessageDialog(null, "Usuário DESATIVADO!");
                        loadUserInformation(user);
                    }
                }
            });


            button_transferUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (user.getOrganizationalUnitIndex() != comboBox_organizationalUnits.getSelectedIndex()) {
                        user.setOrganizationalUnitIndex(comboBox_organizationalUnits.getSelectedIndex());
                        executeCommand(TransferUser(user.getUsername(), user.getOrganizationalUnitIndex()));
                        JOptionPane.showMessageDialog(null, "Usuário TRANSFERIDO!");
                        loadUserInformation(user);
                    }
                }
            });
            comboBox_organizationalUnits.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (comboBox_organizationalUnits.getSelectedIndex() == user.getOrganizationalUnitIndex()) {
                        button_transferUser.setEnabled(false);
                    } else {
                        button_transferUser.setEnabled(true);
                    }
                }
            });
            textField_userLogonWorkstations.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    super.keyReleased(e);

                    if (!Objects.equals(user.getLogonWorkstation(), textField_userLogonWorkstations.getText())) {
                        if (textField_userLogonWorkstations.getText().length() < 16) {
                            button_changeUserLogonWorkstations.setEnabled(true);
                        }
                    } else {
                        button_changeUserLogonWorkstations.setEnabled(false);
                    }
                }
            });
            button_changeUserLogonWorkstations.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textField_userLogonWorkstations.getText().isBlank()) {
                        executeCommand(ChangeUserLogonWorkstations(user.getUsername(), "$null"));
                    } else {
                        executeCommand(ChangeUserLogonWorkstations(user.getUsername(),
                                textField_userLogonWorkstations.getText().trim()));
                    }
                    JOptionPane.showMessageDialog(null, "Equipamento Liberado ALTERADO!");
                    user.setLogonWorkstation(textField_userLogonWorkstations.getText());
                    loadUserInformation(user);
                }
            });
            button_loadAction.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (comboBox_Action.getSelectedIndex()) {
                        case 0 -> {
                            String result = executeCommand(GetUsersWithoutProfilePath());
                            if (result.isBlank()) {
                                textArea_actionResult.setText("Nenhum resultado!");
                            } else {
                                textArea_actionResult.setText(result);
                            }
                        }
                        case 1 -> {
                            String result = executeCommand(GetUsersWithWrongProfilePath());
                            if (result.isBlank()) {
                                textArea_actionResult.setText("Nenhum resultado");
                            } else {
                                textArea_actionResult.setText(result);
                            }
                        }
                        case 2 -> {
                            String result = executeCommand(GetUsersWithoutLogonWorkstations());
                            if (result.isBlank()) {
                                textArea_actionResult.setText("Nenhum resultado");
                            } else {
                                textArea_actionResult.setText(result);
                            }
                        }
                        case 3 -> {
                            String result = executeCommand(GetInactiveUsersOutsideInactiveOU());
                            if (result.isBlank()) {
                                textArea_actionResult.setText("Nenhum resultado");
                            } else {
                                textArea_actionResult.setText(result);
                            }
                        }
                        case 4 -> {
                            String result = executeCommand(GetActiveUsersInInactiveOU());
                            if (result.isBlank()) {
                                textArea_actionResult.setText("Nenhum resultado");
                            } else {
                                textArea_actionResult.setText(result);
                            }
                        }
                    }
                }
            });

            textField_userEmail.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    super.keyReleased(e);

                    if (textField_userEmail.getText().trim().equals(user.getMail())) {
                        button_changeUserEmailAddress.setEnabled(false);
                    } else {
                        button_changeUserEmailAddress.setEnabled(true);
                    }
                }
            });

            button_changeUserEmailAddress.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textField_userEmail.getText().isBlank()) {
                        executeCommand(SetUserEmailAddress(user.getUsername(), "$null"));
                    } else {
                        executeCommand(SetUserEmailAddress(user.getUsername(), textField_userEmail.getText().trim()));
                    }
                    user.setMail(textField_userEmail.getText().trim());
                    JOptionPane.showMessageDialog(applicationPanel, "Endereço de E-Mail ALTERADO!");
                    loadUserInformation(user);
                }
            });

            button_searchComputer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String netbios = textField_computerNetbios.getText().trim();
                    if (computerExists(netbios)) {
                        computer = new Computer(
                                netbios,
                                executeCommand(GetComputerStatus(netbios)),
                                executeCommand(GetComputerLastIP(netbios)),
                                executeCommand(GetComputerLastBoot(netbios)),
                                executeCommand(GetComputerOperatingSystem(netbios))
                        );
                        button_toggleComputerStatus.setEnabled(true);
                        loadComputerInformation(computer);

                    } else {
                        JOptionPane.showMessageDialog(applicationPanel, "Netbios NÃO encontrada!");
                        cleanAllComputerInformations();
                    }
                }
            });


            button_toggleComputerStatus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (computer.getEnabled().equals("True")) {
                        executeCommand(DisableComputer(computer.getNetbios()));
                        computer.setEnabled("False");
                        JOptionPane.showMessageDialog(applicationPanel, "Computador DESATIVADO!");
                    } else {
                        executeCommand(EnableComputer(computer.getNetbios()));
                        computer.setEnabled("True");
                        JOptionPane.showMessageDialog(applicationPanel, "Computador ATIVADO!");
                    }

                    executeCommand(MoveComputer(computer.getNetbios(), computer.getEnabled()));

                    loadComputerInformation(computer);
                }
            });

            button_patrimonial_sendEmail.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (radioButton_movePatrimonyItem.isSelected()) {
                        Email email = new Email(
                                passwordField_patrimoniel_senderPassword.getText(),
                                textField_move_itemNumber.getText(),
                                textField_move_itemActualPlace.getText(),
                                textField_move_itemNewPlace.getText(),
                                textField_move_itemDescription.getText()
                        );
                        JOptionPane.showMessageDialog(applicationPanel,"Enviando e-mail...");
                        email.sendEmail(textField_patrimonial_emailTo.getText());
                        resetAllNewPatrimonyItemTextFields();
                        resetAllNewPatrimonyPhotos();
                    } else if (radioButton_newItem.isSelected()) {
                        Email email = new Email(
                                passwordField_patrimoniel_senderPassword.getText(),
                                textField_newItemNumber.getText(),
                                textField_newItemPlace.getText(),
                                textField_newItemDescription.getText()
                        );
                        email.sendEmail(textField_patrimonial_emailTo.getText(), plaquetaPhotoPath, frontalPhotoPath,
                                traseiraPhotoPath);
                        resetAllMovePatrimonyItemTextFields();
                    }
                    JOptionPane.showMessageDialog(applicationPanel, "E-mail enviado!");

                    passwordField_patrimoniel_senderPassword.setText("");
                }
            });

        } catch (PowerShellNotAvailableException exception) {
            System.out.println("Error: " + exception);
        }
        button_attachPlaquetaPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int response = fileChooser.showOpenDialog(applicationPanel);
                if (response == JFileChooser.APPROVE_OPTION) {
                    plaquetaPhotoPath = fileChooser.getSelectedFile().getAbsolutePath();
                    label_plaqueta.setIcon(new ImageIcon(enabledIcon));
                }
            }
        });
        button_attachFrontalPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int response = fileChooser.showOpenDialog(applicationPanel);
                if (response == JFileChooser.APPROVE_OPTION) {
                    frontalPhotoPath = fileChooser.getSelectedFile().getAbsolutePath();
                    label_frontal.setIcon(new ImageIcon(enabledIcon));
                }
            }
        });
        button_attachTraseiraPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int response = fileChooser.showOpenDialog(applicationPanel);
                if (response == JFileChooser.APPROVE_OPTION) {
                    traseiraPhotoPath = fileChooser.getSelectedFile().getAbsolutePath();
                    label_traseira.setIcon(new ImageIcon(enabledIcon));
                }
            }
        });
        radioButton_newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton_newItem.isSelected()) {
                    radioButton_movePatrimonyItem.setSelected(false);
                    disableMoveItemSection();
                    enableNewItemSection();
                }
            }
        });
        radioButton_movePatrimonyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton_movePatrimonyItem.isSelected()) {
                    radioButton_newItem.setSelected(false);
                    disableNewItemSection();
                    enableMoveItemSection();
                    radioButton_movePatrimonyItem.setSelected(true);
                }
            }
        });
        passwordField_patrimoniel_senderPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (passwordField_patrimoniel_senderPassword.getText().isBlank()) {
                    button_patrimonial_sendEmail.setEnabled(false);
                } else {
                    button_patrimonial_sendEmail.setEnabled(true);
                }
            }
        });
        radioButton_NewUserTab_sexMale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton_NewUserTab_sexMale.isSelected()) {
                    radioButton_NewUserTab_sexFemale.setSelected(false);
                }
            }
        });
        radioButton_NewUserTab_sexFemale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton_NewUserTab_sexFemale.isSelected()) {
                    radioButton_NewUserTab_sexMale.setSelected(false);
                }
            }
        });
        radioButton_NewUserTab_jrtiYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton_NewUserTab_jrtiYes.isSelected()) {
                    radioButton_NewUserTab_jrtiNo.setSelected(false);
                    textField_NewUserTab_jrtiUsername.setEnabled(true);
                    textField_NewUserTab_jrtiUsernamePassword.setEnabled(true);
                }
            }
        });
        radioButton_NewUserTab_jrtiNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton_NewUserTab_jrtiNo.isSelected()) {
                    radioButton_NewUserTab_jrtiYes.setSelected(false);
                    textField_NewUserTab_jrtiUsername.setEnabled(false);
                    textField_NewUserTab_jrtiUsernamePassword.setEnabled(false);
                }
            }
        });
        radioButton_NewUserTab_sysempYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton_NewUserTab_sysempYes.isSelected()) {
                    radioButton_NewUserTab_sysempNo.setSelected(false);
                    textField_NewUserTab_sysempUsername.setEnabled(true);
                    textField_NewUserTab_sysempUsernamePassword.setEnabled(true);
                }
            }
        });
        radioButton_NewUserTab_sysempNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButton_NewUserTab_sysempNo.isSelected()) {
                    radioButton_NewUserTab_sysempYes.setSelected(false);
                    textField_NewUserTab_sysempUsername.setEnabled(false);
                    textField_NewUserTab_sysempUsernamePassword.setEnabled(false);
                }
            }
        });
        button_NewUserTab_sendEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pronoun;
                if (radioButton_NewUserTab_sexMale.isSelected()) {
                    pronoun = "o";
                } else {
                    pronoun = "a";
                }
                if (radioButton_NewUserTab_jrtiYes.isSelected() && radioButton_NewUserTab_sysempYes.isSelected()) {
                    Email email = new Email(
                            textField_NewUserTab_username.getText(),
                            textField_NewUserTab_userPassword.getText(),
                            textField_NewUserTab_userPrinters.getText(),
                            textField_NewUserTab_PrintersPin.getText(),
                            textField_NewUserTab_jrtiUsername.getText(),
                            textField_NewUserTab_jrtiUsernamePassword.getText(),
                            textField_NewUserTab_sysempUsername.getText(),
                            textField_NewUserTab_sysempUsernamePassword.getText(),
                            textField_NewUserTab_userEmail.getText(),
                            passwordField_NewUserTab_senderPassword.getText()
                    );
                    email.sendEmail(textField_NewUserTab_userEmail.getText(), pronoun, true, true);
                } else if (radioButton_NewUserTab_jrtiYes.isSelected() && radioButton_NewUserTab_sysempNo.isSelected()) {
                    Email email = new Email(
                            "Masc",
                            textField_NewUserTab_username.getText(),
                            textField_NewUserTab_userPassword.getText(),
                            textField_NewUserTab_userPrinters.getText(),
                            textField_NewUserTab_PrintersPin.getText(),
                            textField_NewUserTab_jrtiUsername.getText(),
                            textField_NewUserTab_jrtiUsernamePassword.getText(),
                            textField_NewUserTab_userEmail.getText(),
                            passwordField_NewUserTab_senderPassword.getText()
                    );
                    email.sendEmail(textField_NewUserTab_userEmail.getText(), pronoun, true, false);
                } else if (radioButton_NewUserTab_jrtiNo.isSelected() && radioButton_NewUserTab_sysempYes.isSelected()) {
                    Email email = new Email(
                            textField_NewUserTab_username.getText(),
                            textField_NewUserTab_userPassword.getText(),
                            textField_NewUserTab_userPrinters.getText(),
                            textField_NewUserTab_PrintersPin.getText(),
                            textField_NewUserTab_sysempUsername.getText(),
                            textField_NewUserTab_sysempUsernamePassword.getText(),
                            textField_NewUserTab_userEmail.getText(),
                            passwordField_NewUserTab_senderPassword.getText()
                    );
                    email.sendEmail(textField_NewUserTab_userEmail.getText(), pronoun, false, true);
                } else if (radioButton_NewUserTab_jrtiNo.isSelected() && radioButton_NewUserTab_sysempNo.isSelected()) {
                    Email email = new Email(
                            passwordField_NewUserTab_senderPassword.getText(),
                            textField_NewUserTab_userEmail.getText(),
                            "Masc",
                            textField_NewUserTab_username.getText(),
                            textField_NewUserTab_userPassword.getText(),
                            textField_NewUserTab_userPrinters.getText(),
                            textField_NewUserTab_PrintersPin.getText()
                    );
                    email.sendEmail(textField_NewUserTab_userEmail.getText(), pronoun, false, false);
                }
            }
        });
        passwordField_NewUserTab_senderPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if (passwordField_NewUserTab_senderPassword.getText().isBlank()) {
                    button_NewUserTab_sendEmail.setEnabled(false);
                } else {
                    button_NewUserTab_sendEmail.setEnabled(true);
                }
            }
        });
    }

    private void resetAllMovePatrimonyItemTextFields() {
        textField_move_itemNumber.setText("");
        textField_move_itemActualPlace.setText("");
        textField_move_itemNewPlace.setText("");
        textField_move_itemDescription.setText("");
    }

    private void resetAllNewPatrimonyPhotos() {
        plaquetaPhotoPath = "";
        frontalPhotoPath = "";
        traseiraPhotoPath = "";
        label_plaqueta.setIcon(new ImageIcon(disabledIcon));
        label_traseira.setIcon(new ImageIcon(disabledIcon));
        label_frontal.setIcon(new ImageIcon(disabledIcon));
    }

    private void resetAllNewPatrimonyItemTextFields() {
        textField_newItemNumber.setText("");
        textField_newItemPlace.setText("");
        textField_newItemDescription.setText("");
    }

    private void enableMoveItemSection() {
        textField_move_itemNumber.setEnabled(true);
        textField_move_itemActualPlace.setEnabled(true);
        textField_move_itemNewPlace.setEnabled(true);
        textField_move_itemDescription.setEnabled(true);
    }

    private void enableNewItemSection() {
        textField_newItemNumber.setEnabled(true);
        textField_newItemPlace.setEnabled(true);
        textField_newItemDescription.setEnabled(true);
        button_attachPlaquetaPhoto.setEnabled(true);
        button_attachFrontalPhoto.setEnabled(true);
        button_attachTraseiraPhoto.setEnabled(true);
    }

    private void disableNewItemSection() {
        textField_newItemNumber.setEnabled(false);
        textField_newItemPlace.setEnabled(false);
        textField_newItemDescription.setEnabled(false);
        button_attachPlaquetaPhoto.setEnabled(false);
        button_attachFrontalPhoto.setEnabled(false);
        button_attachTraseiraPhoto.setEnabled(false);
    }

    private void disableMoveItemSection() {
        textField_move_itemNumber.setEnabled(false);
        textField_move_itemActualPlace.setEnabled(false);
        textField_move_itemNewPlace.setEnabled(false);
        textField_move_itemDescription.setEnabled(false);
    }

    private void cleanAllComputerInformations() {
        textField_computerNetbios.setText("");
        label_computerStatus.setText("");
        label_computerOperatingSystem.setText("");
        label_computerLastIP.setText("");
        label_computerLastBoot.setText("");
        button_toggleComputerStatus.setText("Habilitar/Desabilitar");
        button_toggleComputerStatus.setEnabled(false);
    }

    private void loadComputerInformation(Computer computer) {
        if (computer.getEnabled().equals("True")) {
            label_computerStatus.setText("HABILITADO");
            label_computerStatus.setForeground(Color.GREEN);
            button_toggleComputerStatus.setIcon(new ImageIcon(disabledIcon));
            button_toggleComputerStatus.setHorizontalTextPosition(SwingConstants.LEFT);
            button_toggleComputerStatus.setText("Desabilitar Computador");
        } else {
            label_computerStatus.setText("DESABILITADO");
            label_computerStatus.setForeground(Color.RED);
            button_toggleComputerStatus.setIcon(new ImageIcon(enabledIcon));
            button_toggleComputerStatus.setHorizontalTextPosition(SwingConstants.LEFT);
            button_toggleComputerStatus.setText("Habilitar Computador");
        }

        label_computerOperatingSystem.setText(computer.getOperatingSystem());
        label_computerLastIP.setText(computer.getLastIP());
        label_computerLastBoot.setText(computer.getLastLogonDate());
    }

    private boolean computerExists(String netbios) {
        String command = "Get-ADComputer -Identity " + netbios;
        return !executeCommand(command).contains("ObjectNotFound");
    }

    private void disableAllUserButtons() {
        button_transferUser.setEnabled(false);
        button_changeUserEmailAddress.setEnabled(false);
        button_toggleUserStatus.setEnabled(false);
    }

    private void enableAllUserButtons() {
        button_transferUser.setEnabled(true);
        button_changeUserEmailAddress.setEnabled(true);
        button_toggleUserStatus.setEnabled(true);
    }

    private void resetAllInformation() {
        label_userCompleteName.setText("");
        textField_userEmail.setText("");
        label_userStatus.setForeground(Color.BLACK);
        label_userStatus.setText("Status do Usuário");
        textField_userEmail.setEnabled(false);
        textField_userLogonWorkstations.setEnabled(false);
    }

    private void loadUserInformation(User user) {
        label_userCompleteName.setText(user.getCompleteName());
        textField_userEmail.setText(user.getMail());
        label_userProfilePath.setText(user.getProfilePath());
        label_userWhenCreated.setText(user.getWhenCreated());
        textField_userLogonWorkstations.setText(user.getLogonWorkstation());
        comboBox_organizationalUnits.setSelectedIndex(user.getOrganizationalUnitIndex());
        textField_userEmail.setEnabled(true);
        textField_userLogonWorkstations.setEnabled(true);
        if (user.getEnabled().contains("True")) {
            userIsActive();
        } else {
            userIsInative();
        }
    }

    private void userIsActive() {
        label_userStatus.setText("Ativado");
        label_userStatus.setForeground(Color.GREEN);
        button_toggleUserStatus.setIcon(new ImageIcon(disabledIcon));
        button_toggleUserStatus.setHorizontalTextPosition(SwingConstants.LEFT);
        button_toggleUserStatus.setText("Desativar");
    }

    private void userIsInative() {
        label_userStatus.setText("Desativado");
        label_userStatus.setForeground(Color.RED);
        button_toggleUserStatus.setIcon(new ImageIcon(enabledIcon));
        button_toggleUserStatus.setHorizontalTextPosition(SwingConstants.LEFT);
        button_toggleUserStatus.setText("Ativar");
    }

    /***
     * Executes an PowerShell command
     * @param command the command you want to execute
     * @return the command output
     */
    public String executeCommand(String command) {
        PowerShellResponse response = powerShell.executeCommand(command);
        return response.getCommandOutput().trim();
    }

    /***
     * Verify if the user exists and returns true or false
     * @param username username inserted in the search user text field
     * @return true or false
     */
    public boolean userExists(String username) {
        String command = "Get-ADUser -Identity " + username;
        return !executeCommand(command).contains("ObjectNotFound");
    }

    /***
     * Clear the text input field
     * @param textField the text field that you want to clear
     */
    public void cleanTextInputField(JTextField textField) {
        textField.setText("");
    }

    public String formatOrganizacionalUnit(String organizationalUnit) {
        String[] ou = organizationalUnit.trim().split(",");
        return ou[1].substring(3);
    }
}