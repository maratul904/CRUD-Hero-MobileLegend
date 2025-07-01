package view;

import controller.HeroController;
import model.Hero;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainForm extends JFrame {
    HeroController controller = new HeroController();
    DefaultTableModel tableModel;
    JTable table;
    JTextField txtId, txtNama;
    JComboBox<String> cmbKategori, cmbGender;

    public MainForm() {
        setTitle("Data Hero Mobile Legend");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabel
        String[] kolom = {"ID", "Nama Hero", "Kategori", "Gender"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Form Input
        JPanel panelInput = new JPanel(new GridLayout(5, 2));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtNama = new JTextField();
        cmbKategori = new JComboBox<>(new String[]{"MAGE", "ASSASSIN", "FIGHTER", "TANK", "MARKSMAN", "SUPPORT"});
        cmbGender = new JComboBox<>(new String[]{"MALE", "FEMALE"});

        panelInput.add(new JLabel("ID:"));
        panelInput.add(txtId);
        panelInput.add(new JLabel("Nama Hero:"));
        panelInput.add(txtNama);
        panelInput.add(new JLabel("Kategori:"));
        panelInput.add(cmbKategori);
        panelInput.add(new JLabel("Gender:"));
        panelInput.add(cmbGender);

        // Tombol
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");

        panelInput.add(btnTambah);
        panelInput.add(btnEdit);
        panelInput.add(btnHapus);
        add(panelInput, BorderLayout.SOUTH);

        // Event
        tampilData();

        btnTambah.addActionListener(e -> {
            Hero h = new Hero(0, txtNama.getText(), cmbKategori.getSelectedItem().toString(), cmbGender.getSelectedItem().toString());
            controller.insertHero(h);
            tampilData();
            resetForm();
        });

        btnEdit.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            Hero h = new Hero(id, txtNama.getText(), cmbKategori.getSelectedItem().toString(), cmbGender.getSelectedItem().toString());
            controller.updateHero(h);
            tampilData();
            resetForm();
        });

        btnHapus.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            controller.deleteHero(id);
            tampilData();
            resetForm();
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int baris = table.getSelectedRow();
                txtId.setText(table.getValueAt(baris, 0).toString());
                txtNama.setText(table.getValueAt(baris, 1).toString());
                cmbKategori.setSelectedItem(table.getValueAt(baris, 2).toString());
                cmbGender.setSelectedItem(table.getValueAt(baris, 3).toString());
            }
        });

        setVisible(true);
    }

    void tampilData() {
        tableModel.setRowCount(0);
        List<Hero> list = controller.getAllHeroes();
        for (Hero h : list) {
            tableModel.addRow(new Object[]{h.getId(), h.getNama(), h.getKategori(), h.getGender()});
        }
    }

    void resetForm() {
        txtId.setText("");
        txtNama.setText("");
        cmbKategori.setSelectedIndex(0);
        cmbGender.setSelectedIndex(0);
    }
}
