package tests.tests;

import ioc.annotations.HelixTest;
import ioc.data.LocalData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.AnnotatedElement;

public class TestDataProvider {
    @DataProvider(name = "primeNumbers")
    public static Object[][] primeNumbers() throws ClassNotFoundException, NoSuchMethodException {
        Object object = Class.forName("tests.tests.TestDataProvider")
                .getMethod("testDataProvider", LocalData.class)
                .getAnnotation(HelixTest.class);
        HelixTest dataTest = (HelixTest) object;
        int count = dataTest.dataDrivenCount();
        Object[][] data = new Object[count][];
        for (int i=0; i<dataTest.dataDrivenCount(); i++) {
            data[i] = new LocalData[]{new LocalData(i+1)};
        }
        return data;
    }

    @Test(dataProvider = "primeNumbers")
    @HelixTest(dataDrivenCount = 7)
    public void testDataProvider(LocalData data) {
        System.out.println("Test count: "+ data.getCount() + ", hashCode: " + data.hashCode());
    }
}
