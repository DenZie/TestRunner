package com.den.utils;

class Progress extends javax.swing.JFrame {

    /** Creates new form Progress */
    public Progress() {
        initComponents();
    }

    private static Progress prograsObj=new Progress();

    //Swing Components

    private void initComponents() {

        ProgressBar = new javax.swing.JProgressBar();
        Value = new javax.swing.JLabel();
        Cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Value.setText("0%");

        Cancel.setText("Cancel");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Value, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(Value, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Cancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    //Swing Components Finished

    //Exit the Program when press cancel button

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(-1);
    }

    //Main Method

    public static void main(String args[]) {
        prograsObj.setVisible(true);
        ProgressBar.setValue(0);
        Process();    //calling the time delay process
    }

    //User Define method(Time Delaying Process)
    //You have to worry about only this method process()
    public static void Process(){
        int x= 0;
        while(x<100000000){
            x++;
            ProgressBar.setValue(x/1000000);
            Value.setText(String.valueOf(x/1000000)+"%");
        }
        prograsObj.setVisible(false);
    }

    //time delaying process end

    // Variables declaration - do not modify
    private static javax.swing.JProgressBar ProgressBar;
    private javax.swing.JButton Cancel;
    private static javax.swing.JLabel Value;
    // End of variables declaration

}


