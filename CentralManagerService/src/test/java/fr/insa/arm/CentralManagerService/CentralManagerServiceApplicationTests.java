package fr.insa.arm.CentralManagerService;

import fr.insa.arm.CentralManagerService.controller.CentralManagerController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CentralManagerServiceApplicationTests {

	@Autowired
	private CentralManagerController centralManagerController;

	@Test
	void contextLoads() {
		assert centralManagerController != null;
	}

	@Test
	void testIsWorkingHours() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		// Working hours : 7-22
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		assert centralManagerController.managerProcess.isWorkingHours(calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		assert !centralManagerController.managerProcess.isWorkingHours(calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 21);
		assert centralManagerController.managerProcess.isWorkingHours(calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		assert !centralManagerController.managerProcess.isWorkingHours(calendar.getTime());
	}

	@Test
	void testMean() {
		ArrayList<Float> values = new ArrayList<>(Arrays.asList(10.0f,20.0f));
		assert centralManagerController.managerProcess.getMean(values) == 15.0f;

		ArrayList<Float> values2 = new ArrayList<>(Arrays.asList(10.0f,20.0f,30.0f));
		assert centralManagerController.managerProcess.getMean(values2) == 20.0f;

		ArrayList<Float> values3 = new ArrayList<>(Arrays.asList(10.0f,20.0f,30.0f,45.0f));
		assert !centralManagerController.managerProcess.getMean(values3) == 26.25f;
	}

}
