/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.professordanilo.quizapp.frame;

import br.com.professordanilo.quizapp.entity.Event;
import br.com.professordanilo.quizapp.util.AppIcons;
import br.com.professordanilo.quizapp.util.ContextDAO;
import br.com.professordanilo.quizapp.util.ContextLogic;
import br.com.professordanilo.quizapp.util.ImageUtil;
import br.com.professordanilo.quizapp.util.StringHelper;
import br.com.professordanilo.quizapp.util.SwingUtil;
import br.com.professordanilo.quizapp.util.exception.BusinessException;
import br.com.professordanilo.quizapp.util.exception.SystemException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author danilo
 */
public class QuizManager extends javax.swing.JFrame {

    private QuizProjectionFrm quizProjectionFrm;
    private GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    private List<Event> events;
    private Event event;

    private QuizManagerState frameState = QuizManagerState.SELECT_EVENT;

    private enum QuizManagerState {
        SELECT_EVENT,
        EDIT_EVENT,
        BATTLE
    }

    /**
     * Creates new form QuizManager
     */
    public QuizManager() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        String[] displays = new String[devices.length];
        for (int i = 0; i < devices.length; i++) {
            displays[i] = "Monitor " + (i + 1);
        }
        ComboBoxModel<String> boxModel = new DefaultComboBoxModel<>(displays);
        cboDevices.setModel(boxModel);
        selectOriginalLogo();
        changeFrameState(QuizManagerState.SELECT_EVENT);
    }

    public void listEvent() {
        try {
            events = ContextLogic.getEventLogic().findAll();
            TableModel model = new DefaultTableModel(new String[]{"Nome"}, events.size());
            for (int i = 0; i < events.size(); i++) {
                model.setValueAt(events.get(i), i, 0);
            }
            tblEvent.setModel(model);
            tblEvent.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
                btnSelectEvent.setEnabled(tblEvent.getSelectedRow() > -1);
            });
        } catch (BusinessException ex) {
            SwingUtil.addMessageWarn(ex);
            Logger.getLogger(QuizManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            SwingUtil.addMessageError(ex);
            Logger.getLogger(QuizManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void selectOriginalLogo() {
        lblViewLogo.setIcon(new ImageIcon(ImageUtil.resizeToMaxValue(AppIcons.DEFAULT_LOGO_BUFFERED, 100, 100)));
    }

    private void selectNewLogo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes()));
        chooser.setAcceptAllFileFilterUsed(false);
        int retorno = chooser.showOpenDialog(rootPane);
        if (retorno == 0) {
            try {
                File fileSelected = chooser.getSelectedFile();
                byte[] bimage = Files.readAllBytes(fileSelected.toPath());
                event.setLogo(bimage);
                saveEvent();
            } catch (IOException ex) {
                Logger.getLogger(QuizManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void updateEvent() {
        txtNameEvent.setText(event.getName());
        spinStopwatch.setValue(event.getStopwatch());
        if (event.getTypeCompetidor() == Event.TypeCompetidor.GROUP) {
            rbGroup.setSelected(true);
        } else {
            rbSingle.setSelected(true);
        }
        updatePreviewLogo();
    }

    private void updatePreviewLogo() {
        try {
            if (event.getLogo() == null || event.getLogo().length <= 0) {
                selectOriginalLogo();
                return;
            }
            InputStream is = new java.io.ByteArrayInputStream(event.getLogo());
            lblViewLogo.setIcon(new ImageIcon(ImageUtil.resizeToMaxValue(ImageIO.read(is), 100, 100)));
        } catch (IOException ex) {
            SwingUtil.addMessageError("Erro ao exibir a imagem.");
            Logger.getLogger(QuizManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeFrameState(QuizManagerState state) {
        tabPaneQuiz.setEnabledAt(QuizManagerState.SELECT_EVENT.ordinal(), false);
        tabPaneQuiz.setEnabledAt(QuizManagerState.EDIT_EVENT.ordinal(), false);
        tabPaneQuiz.setEnabledAt(QuizManagerState.BATTLE.ordinal(), false);
        switch (state) {
            case SELECT_EVENT:
                tabPaneQuiz.setSelectedIndex(QuizManagerState.SELECT_EVENT.ordinal());
                tabPaneQuiz.setEnabledAt(QuizManagerState.SELECT_EVENT.ordinal(), true);
                break;
            case EDIT_EVENT:
                tabPaneQuiz.setEnabledAt(QuizManagerState.EDIT_EVENT.ordinal(), true);
                tabPaneQuiz.setSelectedIndex(QuizManagerState.EDIT_EVENT.ordinal());
                break;
            case BATTLE:
                tabPaneQuiz.setEnabledAt(QuizManagerState.BATTLE.ordinal(), true);
                tabPaneQuiz.setSelectedIndex(QuizManagerState.BATTLE.ordinal());
                break;
        }
    }

    private void saveEvent() {
        try {
            event = ContextLogic.getEventLogic().save(event);
            updateEvent();
        } catch (BusinessException ex) {
            SwingUtil.addMessageWarn(ex);
            Logger.getLogger(QuizManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            SwingUtil.addMessageError(ex);
            Logger.getLogger(QuizManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupCompetidor = new javax.swing.ButtonGroup();
        tabPaneQuiz = new javax.swing.JTabbedPane();
        pnlSelectEvent = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEvent = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnSelectEvent = new javax.swing.JButton();
        btnNewEvent = new javax.swing.JButton();
        btnDeleteEvent = new javax.swing.JButton();
        pnlEditEvent = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPlayers = new javax.swing.JTable();
        btnNewPlayer = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNameEvent = new javax.swing.JTextField();
        spinStopwatch = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rbSingle = new javax.swing.JRadioButton();
        rbGroup = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        btnSelectLogo = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        lblViewLogo = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnSelectOtherEvent = new javax.swing.JButton();
        btnStartBattle = new javax.swing.JButton();
        pnlBattle = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cboDevices = new javax.swing.JComboBox<>();
        chkFullscreen = new javax.swing.JCheckBox();
        btnStartProjection = new javax.swing.JButton();
        btnStopProjection = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnBackToEditEvent = new javax.swing.JButton();
        btnBackToSelectEvent = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        menuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabPaneQuiz.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabPaneQuizStateChanged(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Eventos"));

        tblEvent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEvent.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblEvent);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Navegação"));

        btnSelectEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/dialog-ok.png"))); // NOI18N
        btnSelectEvent.setText("Selecionar Evento");
        btnSelectEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectEventActionPerformed(evt);
            }
        });

        btnNewEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/list-add.png"))); // NOI18N
        btnNewEvent.setText("Novo Evento");
        btnNewEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewEventActionPerformed(evt);
            }
        });

        btnDeleteEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/draw-eraser.png"))); // NOI18N
        btnDeleteEvent.setText("Deletar Evento");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelectEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNewEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteEvent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSelectEvent)
                .addComponent(btnNewEvent)
                .addComponent(btnDeleteEvent))
        );

        javax.swing.GroupLayout pnlSelectEventLayout = new javax.swing.GroupLayout(pnlSelectEvent);
        pnlSelectEvent.setLayout(pnlSelectEventLayout);
        pnlSelectEventLayout.setHorizontalGroup(
            pnlSelectEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSelectEventLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSelectEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSelectEventLayout.setVerticalGroup(
            pnlSelectEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSelectEventLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPaneQuiz.addTab("Selecionar Evento", pnlSelectEvent);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Jogadores"));

        tblPlayers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblPlayers);

        btnNewPlayer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/list-add.png"))); // NOI18N
        btnNewPlayer.setText("Novo Joador");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/gtk-edit.png"))); // NOI18N
        jButton1.setText("Editar Jogador");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/draw-eraser.png"))); // NOI18N
        jButton2.setText("Deletar Jogador");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnNewPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewPlayer)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações"));

        jLabel1.setText("Nome");

        txtNameEvent.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameEventFocusLost(evt);
            }
        });

        spinStopwatch.setModel(new javax.swing.SpinnerNumberModel(5, 0, 15, 1));
        spinStopwatch.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinStopwatchStateChanged(evt);
            }
        });

        jLabel2.setText("Tempo Cronômetro");

        jLabel3.setText("Competidor");

        groupCompetidor.add(rbSingle);
        rbSingle.setText("Individual");
        rbSingle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbSingleActionPerformed(evt);
            }
        });

        groupCompetidor.add(rbGroup);
        rbGroup.setSelected(true);
        rbGroup.setText("Grupo");
        rbGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbGroupActionPerformed(evt);
            }
        });

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Logo para projeção"));

        btnSelectLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/dialog-ok.png"))); // NOI18N
        btnSelectLogo.setText("Selecionar Logo");
        btnSelectLogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectLogoActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/draw-eraser.png"))); // NOI18N
        jButton4.setText("Logo Padrão");

        lblViewLogo.setBackground(new java.awt.Color(255, 255, 255));
        lblViewLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblViewLogo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblViewLogo.setOpaque(true);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSelectLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblViewLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblViewLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnSelectLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinStopwatch, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbGroup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbSingle)
                        .addGap(0, 90, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNameEvent)))
                .addGap(12, 12, 12))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNameEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinStopwatch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(rbSingle)
                    .addComponent(rbGroup))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Navegação"));

        btnSelectOtherEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/undo.png"))); // NOI18N
        btnSelectOtherEvent.setText("Selecionar outro evento");
        btnSelectOtherEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectOtherEventActionPerformed(evt);
            }
        });

        btnStartBattle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/game.png"))); // NOI18N
        btnStartBattle.setText("Iniciar Competição");
        btnStartBattle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartBattleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSelectOtherEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStartBattle, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSelectOtherEvent)
                .addComponent(btnStartBattle))
        );

        javax.swing.GroupLayout pnlEditEventLayout = new javax.swing.GroupLayout(pnlEditEvent);
        pnlEditEvent.setLayout(pnlEditEventLayout);
        pnlEditEventLayout.setHorizontalGroup(
            pnlEditEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditEventLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlEditEventLayout.setVerticalGroup(
            pnlEditEventLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditEventLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPaneQuiz.addTab("Gerenciar Evento", pnlEditEvent);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuraçãoes de Projeção"));

        cboDevices.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        chkFullscreen.setText("Modo Janela");
        chkFullscreen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/view-restore.png"))); // NOI18N
        chkFullscreen.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/view-fullscreen.png"))); // NOI18N
        chkFullscreen.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/view-restore.png"))); // NOI18N
        chkFullscreen.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/view-fullscreen.png"))); // NOI18N
        chkFullscreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFullscreenActionPerformed(evt);
            }
        });

        btnStartProjection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/view-right-new.png"))); // NOI18N
        btnStartProjection.setText("Inicar Projeção");
        btnStartProjection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartProjectionActionPerformed(evt);
            }
        });

        btnStopProjection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/view-right-close.svg.png"))); // NOI18N
        btnStopProjection.setText("Parar Projeção");
        btnStopProjection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopProjectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboDevices, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkFullscreen, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStartProjection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStopProjection)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboDevices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkFullscreen)
                    .addComponent(btnStartProjection)
                    .addComponent(btnStopProjection))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Questões"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 414, Short.MAX_VALUE)
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Navegação"));

        btnBackToEditEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/dialog-ok.png"))); // NOI18N
        btnBackToEditEvent.setText("Gerenciar Evento");
        btnBackToEditEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackToEditEventActionPerformed(evt);
            }
        });

        btnBackToSelectEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/professordanilo/quizapp/images/undo.png"))); // NOI18N
        btnBackToSelectEvent.setText("Selecionar outro evento");
        btnBackToSelectEvent.setMaximumSize(new java.awt.Dimension(220, 32));
        btnBackToSelectEvent.setMinimumSize(new java.awt.Dimension(220, 32));
        btnBackToSelectEvent.setPreferredSize(new java.awt.Dimension(220, 32));
        btnBackToSelectEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackToSelectEventActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackToSelectEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBackToEditEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnBackToEditEvent)
                .addComponent(btnBackToSelectEvent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlBattleLayout = new javax.swing.GroupLayout(pnlBattle);
        pnlBattle.setLayout(pnlBattleLayout);
        pnlBattleLayout.setHorizontalGroup(
            pnlBattleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBattleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBattleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBattleLayout.setVerticalGroup(
            pnlBattleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBattleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPaneQuiz.addTab("Gerenciar Competição", pnlBattle);

        jMenu1.setText("Arquivo");

        jMenu3.setText("jMenu3");
        jMenu1.add(jMenu3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sobre");

        menuAbout.setText("Sobre o App");
        menuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAboutActionPerformed(evt);
            }
        });
        jMenu2.add(menuAbout);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPaneQuiz)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPaneQuiz)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartProjectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartProjectionActionPerformed
        if (quizProjectionFrm == null) {
            quizProjectionFrm = new QuizProjectionFrm();
        }
        if (chkFullscreen.isSelected()) {
            Integer deviceSelecionado = Integer.parseInt(((String) cboDevices.getSelectedItem()).substring(8)) - 1;
            devices[deviceSelecionado].setFullScreenWindow(quizProjectionFrm);

        } else {
            quizProjectionFrm.setVisible(true);
        }
    }//GEN-LAST:event_btnStartProjectionActionPerformed

    private void btnStopProjectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopProjectionActionPerformed
        quizProjectionFrm.dispose();
        quizProjectionFrm = null;
    }//GEN-LAST:event_btnStopProjectionActionPerformed

    private void btnSelectEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectEventActionPerformed
        try {
            event = ContextLogic.getEventLogic().getEntity(((Event) tblEvent.getValueAt(tblEvent.getSelectedRow(), 0)).getId());
            updateEvent();
            changeFrameState(QuizManagerState.EDIT_EVENT);
        } catch (BusinessException ex) {
            SwingUtil.addMessageWarn(ex);
            Logger.getLogger(QuizManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            SwingUtil.addMessageError(ex);
            Logger.getLogger(QuizManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSelectEventActionPerformed

    private void btnNewEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewEventActionPerformed
        String nameEvent = JOptionPane.showInputDialog(rootPane, "Nome:", "Informe o nome do evento.", JOptionPane.INFORMATION_MESSAGE);
        if (StringHelper.isEmpty(nameEvent)) {
            return;
        }
        event = new Event();
        event.setName(nameEvent);
        saveEvent();
        changeFrameState(QuizManagerState.EDIT_EVENT);

    }//GEN-LAST:event_btnNewEventActionPerformed

    private void btnSelectLogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectLogoActionPerformed
        selectNewLogo();
    }//GEN-LAST:event_btnSelectLogoActionPerformed

    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
        new AboutFrm(rootPane).setVisible(true);
    }//GEN-LAST:event_menuAboutActionPerformed

    private void tabPaneQuizStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabPaneQuizStateChanged
        listEvent();
        btnSelectEvent.setEnabled(tblEvent.getSelectedRow() > -1);
    }//GEN-LAST:event_tabPaneQuizStateChanged

    private void spinStopwatchStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinStopwatchStateChanged
        int stopwatch = Integer.parseInt(((JSpinner) evt.getSource()).getValue().toString());
        event.setStopwatch(stopwatch);
        saveEvent();
    }//GEN-LAST:event_spinStopwatchStateChanged

    private void rbSingleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbSingleActionPerformed
        event.setTypeCompetidor(Event.TypeCompetidor.SINGLE);
        saveEvent();
    }//GEN-LAST:event_rbSingleActionPerformed

    private void rbGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbGroupActionPerformed
        event.setTypeCompetidor(Event.TypeCompetidor.GROUP);
        saveEvent();
    }//GEN-LAST:event_rbGroupActionPerformed

    private void btnSelectOtherEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectOtherEventActionPerformed
        event = null;
        changeFrameState(QuizManagerState.SELECT_EVENT);
    }//GEN-LAST:event_btnSelectOtherEventActionPerformed

    private void txtNameEventFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameEventFocusLost
        event.setName(txtNameEvent.getText());
        saveEvent();
    }//GEN-LAST:event_txtNameEventFocusLost

    private void btnStartBattleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartBattleActionPerformed
        changeFrameState(QuizManagerState.BATTLE);
    }//GEN-LAST:event_btnStartBattleActionPerformed

    private void btnBackToSelectEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackToSelectEventActionPerformed
        changeFrameState(QuizManagerState.SELECT_EVENT);
    }//GEN-LAST:event_btnBackToSelectEventActionPerformed

    private void btnBackToEditEventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackToEditEventActionPerformed
        changeFrameState(QuizManagerState.EDIT_EVENT);
    }//GEN-LAST:event_btnBackToEditEventActionPerformed

    private void chkFullscreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFullscreenActionPerformed
        chkFullscreen.setText(chkFullscreen.isSelected()?"Modo Tela Cheia":"Modo Janela");
    }//GEN-LAST:event_chkFullscreenActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackToEditEvent;
    private javax.swing.JButton btnBackToSelectEvent;
    private javax.swing.JButton btnDeleteEvent;
    private javax.swing.JButton btnNewEvent;
    private javax.swing.JButton btnNewPlayer;
    private javax.swing.JButton btnSelectEvent;
    private javax.swing.JButton btnSelectLogo;
    private javax.swing.JButton btnSelectOtherEvent;
    private javax.swing.JButton btnStartBattle;
    private javax.swing.JButton btnStartProjection;
    private javax.swing.JButton btnStopProjection;
    private javax.swing.JComboBox<String> cboDevices;
    private javax.swing.JCheckBox chkFullscreen;
    private javax.swing.ButtonGroup groupCompetidor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblViewLogo;
    private javax.swing.JMenuItem menuAbout;
    private javax.swing.JPanel pnlBattle;
    private javax.swing.JPanel pnlEditEvent;
    private javax.swing.JPanel pnlSelectEvent;
    private javax.swing.JRadioButton rbGroup;
    private javax.swing.JRadioButton rbSingle;
    private javax.swing.JSpinner spinStopwatch;
    private javax.swing.JTabbedPane tabPaneQuiz;
    private javax.swing.JTable tblEvent;
    private javax.swing.JTable tblPlayers;
    private javax.swing.JTextField txtNameEvent;
    // End of variables declaration//GEN-END:variables
}
