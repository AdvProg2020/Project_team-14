package Model.Category;

import org.junit.Assert;
import org.junit.Test;

public class CategoryTest {

    @Test
    public void hasParentCategory(){
        Category category = new Category("attribute", null, "name");
        Assert.assertFalse(category.hasParentCategory());
    }

    @Test
    public void isThereCategoryWithName1(){
       // Category category = new Category("attribute", null, "name");
        Assert.assertFalse(Category.isThereCategoryWithName("Manuel"));
    }

    @Test
    public void isThereCategoryWithName2(){
        Category category = new Category("attribute", null, "name");
        Assert.assertTrue(Category.isThereCategoryWithName("name"));
    }

}
