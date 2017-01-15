package Plat.Hibernate.RestricationObjects;

import Plat.Hibernate.Entities.Brand;
import Plat.Hibernate.Entities.Categories;

import java.util.List;

/**
 * Created by MontaserQasem on 12/11/16.
 */
public class ItemsView {
    private List<Categories> categories;
    private List<Brand> brands;
    private boolean inStock;
    private boolean specialPrices;
    private boolean newArrival;
    private int min;
    private int max;
    private int offset;

    public ItemsView(){
        min = -1 ;
        max = -1;
        offset = 0;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public boolean isSpecialPrices() {
        return specialPrices;
    }

    public void setSpecialPrices(boolean specialPrices) {
        this.specialPrices = specialPrices;
    }

    public boolean isNewArrival() {
        return newArrival;
    }

    public void setNewArrival(boolean newArrival) {
        this.newArrival = newArrival;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
