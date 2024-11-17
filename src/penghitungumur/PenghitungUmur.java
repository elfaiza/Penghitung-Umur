package penghitungumur;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PenghitungUmur extends JFrame {
    private JSpinner dateSpinner;
    private JTextField textFieldUmur, textFieldUlangTahunBerikut;
    private JButton btnHitung;

    public PenghitungUmur() {
        setTitle("Aplikasi Penghitung Umur");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel labelTanggalLahir = new JLabel("Pilih Tanggal Lahir:");
        JLabel labelUmur = new JLabel("Umur (tahun, bulan, hari):");
        JLabel labelUlangTahunBerikut = new JLabel("Hari Ulang Tahun Berikutnya:");

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);

        textFieldUmur = new JTextField();
        textFieldUmur.setEditable(false);
        textFieldUlangTahunBerikut = new JTextField();
        textFieldUlangTahunBerikut.setEditable(false);

        btnHitung = new JButton("Hitung");

        panel.add(labelTanggalLahir);
        panel.add(dateSpinner);
        panel.add(labelUmur);
        panel.add(textFieldUmur);
        panel.add(labelUlangTahunBerikut);
        panel.add(textFieldUlangTahunBerikut);
        panel.add(new JLabel());
        panel.add(btnHitung);

        add(panel, BorderLayout.CENTER);

        btnHitung.addActionListener(e -> hitungUmur());
    }

    private void hitungUmur() {
        Date tanggalLahir = (Date) dateSpinner.getValue();

        Calendar lahir = Calendar.getInstance();
        lahir.setTime(tanggalLahir);

        Calendar sekarang = Calendar.getInstance();

        int tahun = sekarang.get(Calendar.YEAR) - lahir.get(Calendar.YEAR);
        int bulan = sekarang.get(Calendar.MONTH) - lahir.get(Calendar.MONTH);
        int hari = sekarang.get(Calendar.DAY_OF_MONTH) - lahir.get(Calendar.DAY_OF_MONTH);

        if (bulan < 0) {
            tahun--;
            bulan += 12;
        }
        if (hari < 0) {
            bulan--;
            Calendar bulanSebelum = (Calendar) sekarang.clone();
            bulanSebelum.add(Calendar.MONTH, -1);
            hari += bulanSebelum.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        textFieldUmur.setText(tahun + " tahun, " + bulan + " bulan, " + hari + " hari");

        Calendar ulangTahunBerikut = (Calendar) lahir.clone();
        ulangTahunBerikut.set(Calendar.YEAR, sekarang.get(Calendar.YEAR));
        if (ulangTahunBerikut.before(sekarang)) {
            ulangTahunBerikut.add(Calendar.YEAR, 1);
        }

        SimpleDateFormat formatTanggal = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
        String ulangTahunBerikutString = formatTanggal.format(ulangTahunBerikut.getTime());

        textFieldUlangTahunBerikut.setText(ulangTahunBerikutString);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PenghitungUmur app = new PenghitungUmur();
            app.setVisible(true);
        });
    }
}
