import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InventorySystem extends JFrame implements ActionListener {

    JTextField nameField, qtyField, priceField;
    JButton addBtn, updateBtn, deleteBtn, clearBtn;
    JTable table;
    DefaultTableModel model;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> qtys = new ArrayList<>();
    ArrayList<Double> prices = new ArrayList<>();

    int selectedRow = -1;

    public InventorySystem() {
        setTitle("Inventory Management System");
        setSize(850, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MAIN PANEL WITH GRADIENT BACKGROUND
        JPanel mainPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(52, 143, 235),
                        0, getHeight(), new Color(86, 180, 211)
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);

        JLabel title = new JLabel("Inventory Management System");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(220, 20, 500, 40);
        mainPanel.add(title);

        // INPUT PANEL
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));
        inputPanel.setBounds(40, 100, 320, 140);
        inputPanel.setBackground(new Color(255, 255, 255, 180));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Product Details"));

        inputPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Quantity:"));
        qtyField = new JTextField();
        inputPanel.add(qtyField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        mainPanel.add(inputPanel);

        // BUTTONS PANEL
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(1, 4, 10, 10));
        btnPanel.setBounds(40, 260, 760, 50);
        btnPanel.setOpaque(false);

        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        clearBtn = new JButton("Clear");

        JButton[] btns = {addBtn, updateBtn, deleteBtn, clearBtn};
        for (JButton b : btns) {
            b.setBackground(new Color(255, 255, 255));
            b.setFont(new Font("Arial", Font.BOLD, 16));
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            b.addActionListener(this);
            btnPanel.add(b);
        }

        mainPanel.add(btnPanel);

        // TABLE
        model = new DefaultTableModel(new Object[]{"Product Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedRow = table.getSelectedRow();
                nameField.setText(model.getValueAt(selectedRow, 0).toString());
                qtyField.setText(model.getValueAt(selectedRow, 1).toString());
                priceField.setText(model.getValueAt(selectedRow, 2).toString());
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(40, 330, 760, 200);
        mainPanel.add(sp);

        add(mainPanel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addBtn) {
                addItem();

            } else if (e.getSource() == updateBtn) {
                updateItem();

            } else if (e.getSource() == deleteBtn) {
                deleteItem();

            } else if (e.getSource() == clearBtn) {
                clearFields();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void addItem() {
        String name = nameField.getText();
        int qty = Integer.parseInt(qtyField.getText());
        double price = Double.parseDouble(priceField.getText());

        names.add(name);
        qtys.add(qty);
        prices.add(price);

        model.addRow(new Object[]{name, qty, price});
        clearFields();
    }

    void updateItem() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        model.setValueAt(nameField.getText(), selectedRow, 0);
        model.setValueAt(qtyField.getText(), selectedRow, 1);
        model.setValueAt(priceField.getText(), selectedRow, 2);

        clearFields();
    }

    void deleteItem() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a product to delete.");
            return;
        }

        model.removeRow(selectedRow);
        selectedRow = -1;
        clearFields();
    }

    void clearFields() {
        nameField.setText("");
        qtyField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        new InventorySystem();
    }
}
