package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.common.dto.CustomerDTO;
import ch.hslu.appe.fbs.model.db.Bill;
import ch.hslu.appe.fbs.model.db.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class CustomerWrapperTest {

    @Test
    public void dtoFromEntity_when_customerEntity_then_customerDTO() {
        // arrange
        final CustomerWrapper customerWrapper = new CustomerWrapper();

        final int customerId = 1;
        final String prename = "Max";
        final String surname = "Muster";
        final String address = "Bahnhofstrasse 17";
        final int plz = 6210;
        final String city = "Sursee";
        final Customer customerEntity = new Customer();
        customerEntity.setId(customerId);
        customerEntity.setPrename(prename);
        customerEntity.setSurname(surname);
        customerEntity.setAdress(address);
        customerEntity.setPlz(plz);
        customerEntity.setCity(city);

        //act
        final CustomerDTO customerDTO = customerWrapper.dtoFromEntity(customerEntity);

        //assert
        Assert.assertEquals(customerId, customerDTO.getId());
        Assert.assertEquals(prename, customerDTO.getPrename());
        Assert.assertEquals(surname, customerDTO.getSurname());
        Assert.assertEquals(address, customerDTO.getAddress());
        Assert.assertEquals(plz, customerDTO.getPlz());
        Assert.assertEquals(city, customerDTO.getCity());
    }

    @Test
    public void entityFromDTO_when_customerDTO_then_customerEntity() {
        // arrange
        final CustomerWrapper customerWrapper = new CustomerWrapper();

        final int customerId = 1;
        final String prename = "Max";
        final String surname = "Muster";
        final String address = "Bahnhofstrasse 17";
        final int plz = 6210;
        final String city = "Sursee";
        final CustomerDTO customerDTO = new CustomerDTO(customerId, prename, surname, plz, city, address);

        //act
        final Customer customerEntity = customerWrapper.entityFromDTO(customerDTO);

        //assert
        Assert.assertEquals(customerId, customerEntity.getId().intValue());
        Assert.assertEquals(prename, customerEntity.getPrename());
        Assert.assertEquals(surname, customerEntity.getSurname());
        Assert.assertEquals(address, customerEntity.getAdress());
        Assert.assertEquals(plz, customerEntity.getPlz().intValue());
        Assert.assertEquals(city, customerEntity.getCity());
    }
}
