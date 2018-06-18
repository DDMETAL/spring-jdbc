package test;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.EmployeeDAO;
import entity.Employee;

public class TestCase {
	private ApplicationContext ac;
	private EmployeeDAO dao;
	@Before
	/**
	 * �����@Before���εķ���
	 * �����������Է�������֮ǰ��ִ��
	 */
	public void init() {
		String config="springjdbc.xml";
		ac=new ClassPathXmlApplicationContext(config);
		dao=ac.getBean("empDAO",EmployeeDAO.class);
	}
	
	@Test
	//���� ���ӳ�
	public void test1() throws SQLException {
		DataSource ds=ac.getBean("ds",DataSource.class);
		System.out.println(ds.getConnection());
	}
	
	@Test
	//���� save
	public void test2() {
		Employee emp=new Employee();
		emp.setName("���");
		emp.setAge(18);
		emp.setSalary(10000);
		dao.save(emp);
	}
	
	@Test
	//����  findAll
	public void test3() {
		List<Employee> employees=dao.findAll();
		System.out.println(employees);
	}
	
	@Test
	//���� findById
	public void test4() {
		Employee employee=dao.findById(21);
		System.out.println(employee);
	}
	
	@Test
	//���� update
	public void test5() {
		Employee emp=dao.findById(21);
		emp.setAge(21);
		emp.setSalary(emp.getSalary()*2);
		dao.update(emp);
	}
	
	@Test
	//���� delete
	public void test6() {
		dao.delete(21);
	}
}
