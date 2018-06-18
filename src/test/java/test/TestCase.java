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
	 * 添加了@Before修饰的方法
	 * 会在其他测试方法运行之前先执行
	 */
	public void init() {
		String config="springjdbc.xml";
		ac=new ClassPathXmlApplicationContext(config);
		dao=ac.getBean("empDAO",EmployeeDAO.class);
	}
	
	@Test
	//测试 连接池
	public void test1() throws SQLException {
		DataSource ds=ac.getBean("ds",DataSource.class);
		System.out.println(ds.getConnection());
	}
	
	@Test
	//测试 save
	public void test2() {
		Employee emp=new Employee();
		emp.setName("零二");
		emp.setAge(18);
		emp.setSalary(10000);
		dao.save(emp);
	}
	
	@Test
	//测试  findAll
	public void test3() {
		List<Employee> employees=dao.findAll();
		System.out.println(employees);
	}
	
	@Test
	//测试 findById
	public void test4() {
		Employee employee=dao.findById(21);
		System.out.println(employee);
	}
	
	@Test
	//测试 update
	public void test5() {
		Employee emp=dao.findById(21);
		emp.setAge(21);
		emp.setSalary(emp.getSalary()*2);
		dao.update(emp);
	}
	
	@Test
	//测试 delete
	public void test6() {
		dao.delete(21);
	}
}
