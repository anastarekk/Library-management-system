import javax.swing.*;
import java.awt.*;

public class LibraryGUI {
    private Library library;
    private JFrame frame;
    private JTextArea displayArea;
    private JTextField titleField, authorField, isbnField, copiesField, userIdField;

    public LibraryGUI(Library library) {
        this.library = library;
        frame = new JFrame("📚 Library Management System");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // 📌 Display Area
        displayArea = new JTextArea(10, 50);
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 14));
        displayArea.setBorder(BorderFactory.createTitledBorder("Available Books"));
        frame.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // 📌 Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add / Manage Books"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addInputField(inputPanel, gbc, "Title:", titleField = new JTextField(15), 0);
        addInputField(inputPanel, gbc, "Author:", authorField = new JTextField(15), 1);
        addInputField(inputPanel, gbc, "ISBN:", isbnField = new JTextField(15), 2);
        addInputField(inputPanel, gbc, "Copies:", copiesField = new JTextField(5), 3);
        addInputField(inputPanel, gbc, "User ID:", userIdField = new JTextField(10), 4);
        frame.add(inputPanel, BorderLayout.NORTH);

        // 📌 Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("➕ Add Book");
        addButton.addActionListener(e -> addBook());
        buttonPanel.add(addButton);

        JButton listButton = new JButton("📖 List Books");
        listButton.addActionListener(e -> listBooks());
        buttonPanel.add(listButton);

        JButton borrowButton = new JButton("📥 Borrow Book");
        borrowButton.addActionListener(e -> borrowBook());
        buttonPanel.add(borrowButton);

        JButton returnButton = new JButton("📤 Return Book");
        returnButton.addActionListener(e -> returnBook());
        buttonPanel.add(returnButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addInputField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void addBook() {
        try {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String isbn = isbnField.getText().trim();
            String copiesText = copiesField.getText().trim();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || copiesText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "❌ Please fill all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int copies = Integer.parseInt(copiesText);

            // Show dropdown to select the type of item
            String[] itemTypes = {"Physical Book", "eBook", "CD", "DVD", "Journal", "Reference Book"};
            String selectedType = (String) JOptionPane.showInputDialog(frame,
                    "Select the type of item:", "Item Type",
                    JOptionPane.QUESTION_MESSAGE, null, itemTypes, itemTypes[0]);

            if (selectedType == null) {
                JOptionPane.showMessageDialog(frame, "❌ No item type selected!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LibraryItem item;
            switch (selectedType) {
                case "eBook":
                    item = new Ebook(title, author, isbn, copies);
                    break;
                case "CD":
                    item = new CD(title, author, isbn, copies);
                    break;
                case "DVD":
                    item = new DVD(title, author, isbn, copies);
                    break;
                case "Journal":
                    item = new Journal(title, author, isbn, copies);
                    break;
                case "Reference Book":
                    item = new ReferenceBook(title, author, isbn, copies);
                    break;
                default:
                    item = new PhysicalBook(title, author, isbn, copies);
            }

            library.addItem(item);
            JOptionPane.showMessageDialog(frame, "✅ " + selectedType + " added: " + title, "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields
            titleField.setText("");
            authorField.setText("");
            isbnField.setText("");
            copiesField.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "❌ Copies must be a number!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void listBooks() {
        String books = library.Print();  // Get the list of books

        if (books == null || books.isEmpty()) {  // Avoid NullPointerException
            books = "No books available.";
        }

        displayArea.setText("📖 Available Books:\n" + books);
    }


    private void borrowBook() {
        String userId = userIdField.getText().trim();
        String isbn = isbnField.getText().trim();

        if (userId.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "❌ Enter User ID & ISBN!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LibraryUser user = library.getUser(userId);
        LibraryItem item = library.getItem(isbn);

        if (user == null) {
            JOptionPane.showMessageDialog(frame, "❌ User not found! Only predefined users can borrow books.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (item == null || item.getAvailableQuantity() == 0 ) {
            JOptionPane.showMessageDialog(frame, "❌ Book unavailable!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(!(item instanceof Borrowable)){
            JOptionPane.showMessageDialog(frame, "❌ Cannot borrow this book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
            else {
            user.borrowItem(item);
            JOptionPane.showMessageDialog(frame, "✅ Book borrowed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void returnBook() {
        String userId = userIdField.getText().trim();
        String isbn = isbnField.getText().trim();

        if (userId.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "❌ Enter User ID & ISBN!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LibraryUser user = library.getUser(userId);
        LibraryItem item = library.getItem(isbn);

        if (user == null) {
            JOptionPane.showMessageDialog(frame, "❌ User not found! Only predefined users can return books.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (item == null) {
            JOptionPane.showMessageDialog(frame, "❌ Invalid book details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(!user.returnItem(item)){
            JOptionPane.showMessageDialog(frame, "❌ You did not borrow this book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
            else {
            user.returnItem(item);
            JOptionPane.showMessageDialog(frame, "✅ Book returned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public static void main(String[] args) {
        Library lib = new Library();
        SwingUtilities.invokeLater(() -> new LibraryGUI(lib));
    }
}
