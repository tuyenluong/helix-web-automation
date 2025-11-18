package helix.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.lang.reflect.Field;

public class FindByBuilt extends AbstractFindByBuilder {

    @Override
    public By buildIt(Object annotation, Field field) {
        buildFindBy(annotation, field);
        buildFindBys(annotation, field);
        buildFindAll(annotation, field);
        return null;
    }

    private By buildFindBy(Object annotation, Field field){
        if(field.isAnnotationPresent(FindBy.class)){
            FindBy findBy = (FindBy)annotation;
            this.assertValidFindBy(findBy);
            By ans = this.buildByFromShortFindBy(findBy);
            if (ans == null) {
                ans = this.buildByFromLongFindBy(findBy);
            }
            return ans;
        }
        return null;
    }

    private By buildFindBys(Object annotation, Field field){
        if(field.isAnnotationPresent(FindBy.class)){
            FindBys findBys = (FindBys)annotation;
            this.assertValidFindBys(findBys);
            FindBy[] findByArray = findBys.value();
            By[] byArray = new By[findByArray.length];

            for(int i = 0; i < findByArray.length; ++i) {
                byArray[i] = this.buildByFromFindBy(findByArray[i]);
            }

            return new ByChained(byArray);
        }
        return null;
    }
    private By buildFindAll(Object annotation, Field field){
        if(field.isAnnotationPresent(FindBy.class)){
            FindAll findBys = (FindAll)annotation;
            this.assertValidFindAll(findBys);
            FindBy[] findByArray = findBys.value();
            By[] byArray = new By[findByArray.length];

            for(int i = 0; i < findByArray.length; ++i) {
                byArray[i] = this.buildByFromFindBy(findByArray[i]);
            }

            return new ByAll(byArray);
        }
        return null;
    }
}
