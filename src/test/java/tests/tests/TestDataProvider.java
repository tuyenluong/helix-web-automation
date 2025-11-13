package tests.tests;

import ioc.Data;
import ioc.data.LocalData;
import ioc.listeners.SuiteListener;
import ioc.listeners.TestListener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({SuiteListener.class, TestListener.class})
public class TestDataProvider {
    @DataProvider(name = "primeNumbers")
    public static Object[][] primeNumbers() throws ClassNotFoundException, NoSuchMethodException {
        Object object = Class.forName("tests.tests.TestDataProvider")
                .getMethod("testDataProvider", LocalData.class)
                .getAnnotation(Data.class);
        Data dataTest = (Data) object;
        int count = dataTest.dataDrivenCount();
        Object[][] data = new Object[count][];
        for (int i=0; i<dataTest.dataDrivenCount(); i++) {
            data[i] = new LocalData[]{new LocalData(i+1)};
        }
        return data;
    }

    @Test(dataProvider = "primeNumbers")
    @Data()
    public void testDataProvider(LocalData data) {
        System.out.println("Test count: "+ data.getCount() + ", hashCode: " + data.hashCode());
    }
}
