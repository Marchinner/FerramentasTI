# Ferramentas T.I

Este é a versão pública do programa desenvolvido inteiramente por mim para suprir e otimizar algumas demandas no setor
de T.I da empresa Grupo Dok.
Foi desenvolvido inteiramente em Java e utilizei dois plugins Maven para algumas funcionalidades, sendo eles:
- JPowerShell, para execução de comandos PowerShell
- Javax Mail, para o envio de e-mails

Durante o desenvolvimento deste programa, coloquei em prática conceitos de POO, upload e envio de arquivos,
envio de e-mails, execução de comandos e scripts PowerShell e criação de janelas com Java Swing.

## Screenshots
![App Screenshot](https://github.com/Marchinner/FerramentasTI/blob/master/src/main/resources/telaUtilitarios.png)
![App Screenshot](https://github.com/Marchinner/FerramentasTI/blob/master/src/main/resources/telaUsuarios.png)
![App Screenshot](https://github.com/Marchinner/FerramentasTI/blob/master/src/main/resources/telaComputadores.png)
![App Screenshot](https://github.com/Marchinner/FerramentasTI/blob/master/src/main/resources/telaPatrimonio.png)
![App Screenshot](https://github.com/Marchinner/FerramentasTI/blob/master/src/main/resources/telaCredenciais.png)

## Funcionalidades

- Comandos para verificações rápidas de segurança/organização e integridade do Active Directory
- Buscar e editar informações do usuário, mover, ativar, alterar e-mail e computador liberado para logon
- Buscar e habilitar/desabilitar computadores da rede
- Enviar uma mensagem automática conforme o formulário para informar a movimentação de um item de patrimônio
- Enviar uma mensagem automática para um novo item de patrimônio e anexar as fotos necessárias para cadastro
- Enviar uma mensagem automática de "boas vindas" que informa ao novo colaborador sobre suas credenciais e acessos


## Tecnologias
- Java JDK 21
- Java Swing
- Maven
- JPowerShell Maven Plugin
- Javax Mail Maven Plugin

## Observações
Esta é uma versão com código fonte modificado para se tornar pública, portanto, as funcionalidades que precisam de PowerShell e o envio de e-mail não funcionarão pois dependem de informações e acessos que são privadas da empresa.
Você pode rodar o aplicativo e visualizar todo o seu frontend e sua lógica utilizada no backend, porém as funcionalidades não estão com os dados.
