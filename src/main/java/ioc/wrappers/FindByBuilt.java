package ioc.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.FindBy;

import java.lang.reflect.Field;

public class FindByBuilt extends AbstractFindByBuilder {

    @Override
    public By buildIt(Object annotation, Field field) {
        FindBy findBy = (FindBy)annotation;
        this.assertValidFindBy(findBy);
        By ans = this.buildByFromShortFindBy(findBy);
        if (ans == null) {
            ans = this.buildByFromLongFindBy(findBy);
        }
        return ans;
    }
}
