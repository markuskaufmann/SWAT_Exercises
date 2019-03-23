package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.BillDTO;
import ch.hslu.appe.fbs.model.db.Bill;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class BillWrapperTest {

    @Test
    public void dtoFromEntity_when_billEntity_then_billDTO() {
        // arrange
        final BillWrapper billWrapper = new BillWrapper();

        final int billId = 1;
        final int price = 10;
        final int orderId = 2;
        final Bill billEntity = new Bill();
        billEntity.setId(billId);
        billEntity.setPrice(price);
        billEntity.setOrderId(orderId);

        //act
        final BillDTO billDTO = billWrapper.dtoFromEntity(billEntity);

        //assert
        Assert.assertEquals(billId, billDTO.getId().intValue());
        Assert.assertEquals(price, billDTO.getPrice().intValue());
        Assert.assertEquals(orderId, billDTO.getOrderId().intValue());
    }

    @Test
    public void entityFromDTO_when_billDTO_then_billEntity() {
        // arrange
        final BillWrapper billWrapper = new BillWrapper();

        final int billId = 1;
        final int price = 10;
        final int orderId = 2;
        final BillDTO billDTO = new BillDTO(billId, orderId, price);

        //act
        final Bill billEntity = billWrapper.entityFromDTO(billDTO);

        //assert
        Assert.assertEquals(billId, billEntity.getId().intValue());
        Assert.assertEquals(price, billEntity.getPrice().intValue());
        Assert.assertEquals(orderId, billEntity.getOrderId().intValue());
    }
}
