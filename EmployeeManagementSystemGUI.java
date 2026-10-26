import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class EmployeeManagementSystemGUI extends JFrame implements ActionListener {

    private JTextField tfId, tfName, tfFather, tfEmail, tfContact;
    private JTextField tfTotalDays, tfPresentDays, tfFinalSalary;
    private JComboBox<String> cbDesignation;
    private JTextArea displayArea;

    private JButton btnAdd, btnView, btnClear, btnExit, btnCalculate;

    double baseSalary = 0;

    public EmployeeManagementSystemGUI() {

        setTitle("Employee Management System - Vishal Singh");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("EMPLOYEE MANAGEMENT SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(52, 73, 94));
        title.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(9,2,10,10));
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));
        panel.setBackground(new Color(236,240,241));

        tfId = new JTextField();
        tfName = new JTextField();
        tfFather = new JTextField();
        tfEmail = new JTextField(28);
        tfContact = new JTextField();

        String[] designations = {"Manager", "Developer", "HR", "Clerk"};
        cbDesignation = new JComboBox<>(designations);

        tfTotalDays = new JTextField();
        tfPresentDays = new JTextField();
        tfFinalSalary = new JTextField();
        tfFinalSalary.setEditable(false);

        panel.add(new JLabel("Employee ID:")); panel.add(tfId);
        panel.add(new JLabel("Name:")); panel.add(tfName);
        panel.add(new JLabel("Father Name:")); panel.add(tfFather);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Contact:")); panel.add(tfContact);
        panel.add(new JLabel("Designation:")); panel.add(cbDesignation);
        panel.add(new JLabel("Total Working Days:")); panel.add(tfTotalDays);
        panel.add(new JLabel("Present Days:")); panel.add(tfPresentDays);
        panel.add(new JLabel("Final Salary:")); panel.add(tfFinalSalary);

        add(panel, BorderLayout.WEST);

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setBackground(Color.BLACK);
        displayArea.setForeground(Color.GREEN);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2,3,10,10));
        buttonPanel.setBackground(new Color(189,195,199));

        btnAdd = createButton("Add");
        btnView = createButton("View");
        btnCalculate = createButton("Calculate Salary");
        btnClear = createButton("Clear");
        btnExit = createButton("Exit");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);

        add(buttonPanel, BorderLayout.SOUTH);

        setEnterKeyFocus(); // ENTER key functionality

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.addActionListener(this);
        return btn;
    }

    // ENTER moves to next field
    private void setEnterKeyFocus() {
        JTextField[] fields = {tfId, tfName, tfFather, tfEmail, tfContact, tfTotalDays, tfPresentDays};

        for (int i = 0; i < fields.length - 1; i++) {
            fields[i].addActionListener(e -> fields[fields.length - 1].requestFocus());
        }
    }

    // Designation Salary
    private void setBaseSalary() {
        String des = (String) cbDesignation.getSelectedItem();

        if (des.equals("Manager")) baseSalary = 50000;
        else if (des.equals("Developer")) baseSalary = 40000;
        else if (des.equals("HR")) baseSalary = 30000;
        else if (des.equals("Clerk")) baseSalary = 20000;
    }

    public void actionPerformed(ActionEvent e) {

        String id = tfId.getText().trim();

        if (e.getSource() == btnCalculate) {
            try {
                setBaseSalary();
                int totalDays = Integer.parseInt(tfTotalDays.getText());
                int presentDays = Integer.parseInt(tfPresentDays.getText());

                double perDay = baseSalary / totalDays;
                double finalSalary = perDay * presentDays;

                tfFinalSalary.setText(String.valueOf(finalSalary));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid attendance!");
            }
        }

        if (e.getSource() == btnAdd) {
            try {
                FileWriter writer = new FileWriter("Employee_" + id + ".txt");

                writer.write("Employee ID: " + id + "\n");
                writer.write("Name: " + tfName.getText() + "\n");
                writer.write("Father Name: " + tfFather.getText() + "\n");
                writer.write("Email: " + tfEmail.getText() + "\n");
                writer.write("Contact: " + tfContact.getText() + "\n");
                writer.write("Designation: " + cbDesignation.getSelectedItem() + "\n");
                writer.write("Base Salary: " + baseSalary + "\n");
                writer.write("Total Days: " + tfTotalDays.getText() + "\n");
                writer.write("Present Days: " + tfPresentDays.getText() + "\n");
                writer.write("Final Salary: " + tfFinalSalary.getText() + "\n");

                writer.close();
                JOptionPane.showMessageDialog(this, "Employee Saved!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }

        if (e.getSource() == btnView) {
            try {
                Scanner sc = new Scanner(new File("Employee_" + id + ".txt"));
                displayArea.setText("");
                while (sc.hasNextLine()) {
                    displayArea.append(sc.nextLine() + "\n");
                }
                sc.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }
        }

        if (e.getSource() == btnClear) {
            tfId.setText("");
            tfName.setText("");
            tfFather.setText("");
            tfEmail.setText("");
            tfContact.setText("");
            tfTotalDays.setText("");
            tfPresentDays.setText("");
            tfFinalSalary.setText("");
            displayArea.setText("");
        }

        if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new EmployeeManagementSystemGUI();
    }
}