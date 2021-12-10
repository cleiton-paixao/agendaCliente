
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtname;
	private JTextField txtmail;
	private JTextField txtphone;
	private JTextField txtcompany;
	private JTextField txtcpf;
	private JTable table;
	private JTextField txtcpfsearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		createConnection();
		table_load();
	}
	 
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;
	
	private void createConnection() {
	    try {
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost/java-crud","root","");
	        System.out.println("Connected to Database.");	        
	    } catch (SQLException e) {
	        throw new RuntimeException("Cannot connect to database", e);
	    }
	}

	public void table_load()
	{
		try {
			pst = conn.prepareStatement("SELECT * FROM customers");
			rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.setBounds(100, 100, 924, 719);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Minha Agenda");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(411, 23, 225, 27);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cadastro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(22, 105, 452, 306);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel inputname = new JLabel("Nome");
		inputname.setFont(new Font("Tahoma", Font.BOLD, 14));
		inputname.setBounds(21, 49, 53, 22);
		panel.add(inputname);
		
		JLabel inputmail = new JLabel("E-mail");
		inputmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		inputmail.setBounds(21, 98, 53, 22);
		panel.add(inputmail);
		
		JLabel inputcpf = new JLabel("CPF");
		inputcpf.setFont(new Font("Tahoma", Font.BOLD, 14));
		inputcpf.setBounds(21, 198, 38, 22);
		panel.add(inputcpf);
		
		JLabel inputempresa = new JLabel("Empresa");
		inputempresa.setFont(new Font("Tahoma", Font.BOLD, 14));
		inputempresa.setBounds(21, 240, 79, 22);
		panel.add(inputempresa);
		
		JLabel inputphone = new JLabel("Telefone");
		inputphone.setFont(new Font("Tahoma", Font.BOLD, 14));
		inputphone.setBounds(21, 152, 65, 22);
		panel.add(inputphone);
		
		txtname = new JTextField();
		txtname.setBounds(94, 46, 330, 32);
		panel.add(txtname);
		txtname.setColumns(10);
		
		txtmail = new JTextField();
		txtmail.setColumns(10);
		txtmail.setBounds(94, 95, 330, 32);
		panel.add(txtmail);
		
		txtphone = new JTextField();
		txtphone.setColumns(10);
		txtphone.setBounds(96, 149, 330, 32);
		panel.add(txtphone);
		
		txtcompany = new JTextField();
		txtcompany.setColumns(10);
		txtcompany.setBounds(94, 237, 330, 32);
		panel.add(txtcompany);
		
		txtcpf = new JTextField();
		txtcpf.setColumns(10);
		txtcpf.setBounds(94, 195, 330, 32);
		panel.add(txtcpf);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					String name,mail,phone,cpf,company;
					
					name = txtname.getText();
					mail = txtmail.getText();
					phone = txtphone.getText();
					cpf = txtcpf.getText();
					company = txtcompany.getText();

					long countName = name.chars().filter(ch -> ch != ' ').count();
					long countMail = mail.chars().filter(ch -> ch != ' ').count();
					long countPhone = phone.chars().filter(ch -> ch != ' ').count();
					long countCpf = cpf.chars().filter(ch -> ch != ' ').count();
					long countCompany = company.chars().filter(ch -> ch != ' ').count();
					
					if(countName < 1 || countMail < 1 || countPhone < 1 || countCpf < 1 || countCompany < 1) {
						JOptionPane.showMessageDialog(null,"Preencha todos os dados para continuar");
						return;
					}
					 
					try {
						
						pst = conn.prepareStatement("INSERT INTO customers(name,mail,phone,cpf,empresa) VALUES (?,?,?,?,?)");
						pst.setString(1, name);
						pst.setString(2, mail);
						pst.setString(3, phone);
						pst.setString(4, cpf);
						pst.setString(5, company);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Cliente Cadastrado com sucesso!");
						table_load();
						txtname.setText("");
						txtmail.setText("");
						txtphone.setText("");
						txtcpf.setText("");
						txtcompany.setText("");
						
						txtname.requestFocus();
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				
			}
		});
		btnNewButton.setBounds(22, 422, 142, 44);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSair.setBounds(174, 422, 138, 44);
		frame.getContentPane().add(btnSair);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				txtname.setText("");
                txtmail.setText("");
                txtphone.setText("");
                txtcpf.setText("");	
                txtcompany.setText("");
			}
		});
		btnLimpar.setBounds(322, 422, 152, 44);
		frame.getContentPane().add(btnLimpar);
		
		JScrollPane listcustomers = new JScrollPane();
		listcustomers.setBounds(484, 113, 414, 353);
		frame.getContentPane().add(listcustomers);
		
		table = new JTable();
		listcustomers.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Buscar Clientes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(22, 493, 452, 100);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblInformeOCpf = new JLabel("Informe o ID");
		lblInformeOCpf.setBounds(10, 45, 119, 17);
		lblInformeOCpf.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblInformeOCpf);
		
		txtcpfsearch = new JTextField();
		txtcpfsearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
				
		            String id = txtcpfsearch.getText();
		 
		                pst = conn.prepareStatement("select name,mail,phone,cpf,empresa from customers where id = ?");
		                pst.setString(1, id);
		                ResultSet rs = pst.executeQuery();
		 
		            if(rs.next()==true)
		            {
		              
		                String name = rs.getString(1);
		                String mail= rs.getString(2);
		                String phone= rs.getString(2);
		                String cpf = rs.getString(3);
		                String empresa = rs.getString(4);

		                
		                txtname.setText(name);
		                txtmail.setText(mail);
		                txtphone.setText(phone);
		                txtcpf.setText(cpf);	
		                txtcompany.setText(empresa);
		                
		            }  
		            else
		            {
		            	 txtname.setText("");
			                txtmail.setText("");
			                txtphone.setText("");
			                txtcpf.setText("");	
			                txtcompany.setText("");
		                
		            }
		            
		 
		 
		        }
		catch (SQLException ex) {
		          
		        }
				
			}
		});
		txtcpfsearch.setBounds(136, 37, 255, 36);
		txtcpfsearch.setColumns(10);
		panel_1.add(txtcpfsearch);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name,mail,phone,cpf,company,bid;
				
				name = txtname.getText();
				mail = txtmail.getText();
				phone = txtphone.getText();
				cpf = txtcpf.getText();
				company = txtcompany.getText();				
				bid = txtcpfsearch.getText();
				
				long countName = name.chars().filter(ch -> ch != ' ').count();
				long countMail = mail.chars().filter(ch -> ch != ' ').count();
				long countPhone = phone.chars().filter(ch -> ch != ' ').count();
				long countCpf = cpf.chars().filter(ch -> ch != ' ').count();
				long countCompany = company.chars().filter(ch -> ch != ' ').count();
				
				if(countName < 1 || countMail < 1 || countPhone < 1 || countCpf < 1 || countCompany < 1) {
					JOptionPane.showMessageDialog(null,"Preencha todos os dados para continuar");
					return;
				}
				 
				try {
					
					pst = conn.prepareStatement("UPDATE customers SET name = ? ,mail= ?, phone = ?, cpf = ? , empresa = ? WHERE id = ?");
					pst.setString(1, name);
					pst.setString(2, mail);
					pst.setString(3, phone);
					pst.setString(4, cpf);
					pst.setString(5, company);
					pst.setString(6, bid);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Cliente Atualizado com sucesso!");
					table_load();
					txtname.setText("");
					txtmail.setText("");
					txtphone.setText("");
					txtcpf.setText("");
					txtcompany.setText("");
					
					txtname.requestFocus();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnAtualizar.setBounds(559, 493, 152, 44);
		frame.getContentPane().add(btnAtualizar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				 String bid;
				 					
					bid = txtcpfsearch.getText();
					
					int input = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir ?", "Atenção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
					
					if(input == JOptionPane.OK_OPTION)
					{
						try {
							
							pst = conn.prepareStatement("DELETE FROM customers WHERE id = ?");						
							pst.setString(1, bid);
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Cliente Excluido com sucesso!");
							table_load();						
							txtname.requestFocus();
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
													
			}
		});
		btnExcluir.setBounds(746, 493, 152, 44);
		frame.getContentPane().add(btnExcluir);
	}
}
