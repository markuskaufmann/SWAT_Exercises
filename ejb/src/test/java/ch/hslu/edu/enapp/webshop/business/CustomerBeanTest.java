package ch.hslu.edu.enapp.webshop.business;

import ch.hslu.edu.enapp.webshop.dto.Customer;
import ch.hslu.edu.enapp.webshop.entity.CustomerEntity;
import ch.hslu.edu.enapp.webshop.exception.NoCustomerFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class CustomerBeanTest {

    @InjectMocks
    private CustomerBean customerBean;

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<CustomerEntity> customerQueryMock;

    @Before
    public void initialize() {
        this.customerBean = new CustomerBean();
        MockitoAnnotations.initMocks(this);
        when(this.entityManagerMock.createNamedQuery("getCustomers", CustomerEntity.class)).thenReturn(this.customerQueryMock);
        when(this.entityManagerMock.createNamedQuery("getCustomerByName", CustomerEntity.class)).thenReturn(this.customerQueryMock);
        when(this.customerQueryMock.getResultList()).thenReturn(this.getDummyCustomers());
    }

    @Test
    public void test_GetCustomers() {
        final List<Customer> customers = this.customerBean.getCustomers();
        Assert.assertEquals(2, customers.size());
    }

    @Test
    public void test_getCustomerByName_ShouldReturnOneMatch_CustomerOne() {
        //TODO write test
    }

    @Test
    public void test_getCustomerByName_ShouldReturnOneMatch_CustomerTwo() {
        //TODO write test
    }

    @Test(expected = NoCustomerFoundException.class)
    public void test_getCustomerByName_ShouldThrowException() throws NoCustomerFoundException {
        when(this.customerQueryMock.getResultList()).thenReturn(Collections.emptyList());
        this.customerBean.getCustomerByName("maxmeier");
    }

    @Test
    public void test_DoesUserNameExist_ShouldReturnTrue() {
        //TODO write test
    }

    @Test
    public void test_DoesUserNameExist_ShouldReturnFalse() {
        //TODO write test
    }

    private List<CustomerEntity> getDummyCustomers() {
        final List<CustomerEntity> customers = new ArrayList<>();

        final CustomerEntity customerOne = new CustomerEntity();
        customerOne.setFirstname("Max");
        customerOne.setLastname("Muster");
        customerOne.setName("maxmuster");
        customers.add(customerOne);

        final CustomerEntity customerTwo = new CustomerEntity();
        customerTwo.setFirstname("Moritz");
        customerTwo.setLastname("Meier");
        customerTwo.setName("moritzmeier");
        customers.add(customerTwo);

        return customers;
    }
}
