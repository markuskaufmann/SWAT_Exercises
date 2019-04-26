package ch.hslu.appe.fbs.business.reorder;

import ch.hslu.appe.fbs.business.bill.BillManager;
import ch.hslu.appe.fbs.business.item.ItemManager;
import ch.hslu.appe.fbs.business.order.OrderManagerImpl;
import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.orderitem.OrderItemPersistor;
import ch.hslu.appe.fbs.data.orderstate.OrderStatePersistor;
import ch.hslu.appe.fbs.data.orderstate.OrderStates;
import ch.hslu.appe.fbs.data.reorder.ReorderPersistor;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.*;
import ch.hslu.appe.fbs.wrapper.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public final class ReorderManagerImplTest {

    @Mock
    private ReorderPersistor reorderPersistor;

    @Mock
    private ItemManager itemManager;

    private ReorderManager reorderManager;

    private List<Reorder> reorderTestees;

    private UserDTO userTestee;
    private UserDTO unauthorizedUserTestee;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.reorderManager = new ReorderManagerImpl(this.reorderPersistor, this.itemManager);
        this.reorderTestees = getReorderTestees();
        this.userTestee = getUserTestee();
        this.unauthorizedUserTestee = getUnauthorizedUserTestee();
        when(this.reorderPersistor.getAll()).thenReturn(this.reorderTestees);
        when(this.reorderPersistor.getById(this.reorderTestees.get(0).getId())).thenReturn(Optional.of(this.reorderTestees.get(0)));
        when(this.reorderPersistor.getById(this.reorderTestees.get(1).getId())).thenReturn(Optional.of(this.reorderTestees.get(1)));
        when(this.reorderPersistor.getById(this.reorderTestees.get(2).getId())).thenReturn(Optional.of(this.reorderTestees.get(2)));
    }

    @Test
    public void getAllReorders_WhenReordersExist_ThenReturnThemAll() throws UserNotAuthorisedException {
        final List<ReorderDTO> reordersDTO = this.reorderManager.getAllReorders(this.userTestee);
        assertEquals(this.reorderTestees.size(), reordersDTO.size());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void getAllReorders_WhenReordersExistButUserNotAuthorized_ThrowException() throws UserNotAuthorisedException {
        this.reorderManager.getAllReorders(this.unauthorizedUserTestee);
    }

    @Test
    public void markReorderAsDelivered_WhenUndeliveredReorderExists_MarkItAsDelivered() throws UserNotAuthorisedException {
        final Reorder undeliveredReorder = this.reorderTestees.get(0);

        doNothing().when(this.itemManager).refillItemStock(any(ItemDTO.class), any(Integer.class));
        doAnswer(invocationOnMock -> {
            final Reorder reorderInvoked = invocationOnMock.getArgument(0);
            undeliveredReorder.setDelivered(reorderInvoked.getDelivered());

            return null;
        }).when(this.reorderPersistor).save(any(Reorder.class));

        this.reorderManager.markReorderAsDelivered(undeliveredReorder.getId(), this.userTestee);

        assertNotNull(undeliveredReorder.getDelivered());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void markReorderAsDelivered_WhenUndeliveredReorderExistsButUserNotAuthorized_ThrowException() throws UserNotAuthorisedException {
        final Reorder undeliveredReorder = this.reorderTestees.get(0);
        this.reorderManager.markReorderAsDelivered(undeliveredReorder.getId(), this.unauthorizedUserTestee);
    }

    @Test
    public void markReorderAsDelivered_WhenReorderAlreadyDelivered_DoNothing() throws UserNotAuthorisedException {
        final Reorder deliveredReorder = this.reorderTestees.get(2);
        final Timestamp expectedDelivered = deliveredReorder.getDelivered();

        doNothing().when(this.itemManager).refillItemStock(any(ItemDTO.class), any(Integer.class));
        doAnswer(invocationOnMock -> {
            final Reorder reorderInvoked = invocationOnMock.getArgument(0);
            deliveredReorder.setDelivered(reorderInvoked.getDelivered());

            return null;
        }).when(this.reorderPersistor).save(any(Reorder.class));

        this.reorderManager.markReorderAsDelivered(deliveredReorder.getId(), this.userTestee);

        assertEquals(expectedDelivered, deliveredReorder.getDelivered());
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void markReorderAsDelivered_WhenReorderAlreadyDeliveredButUserNotAuthorized_ThrowException() throws UserNotAuthorisedException {
        final Reorder deliveredReorder = this.reorderTestees.get(2);
        this.reorderManager.markReorderAsDelivered(deliveredReorder.getId(), this.unauthorizedUserTestee);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.DATATYPIST.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private UserDTO getUnauthorizedUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, "unauthorized");
        return new UserDTO(42, userRoleDTO, "unauthorizedUser");
    }

    private List<Reorder> getReorderTestees() {
        final List<Reorder> reorderTestees = new ArrayList<>();
        final List<Item> itemTestees = getItemTestees();

        final Reorder reorderTestee1 = createReorderTestee(1, 10, false, itemTestees.get(0));
        reorderTestees.add(reorderTestee1);

        final Reorder reorderTestee2 = createReorderTestee(2, 5, false, itemTestees.get(1));
        reorderTestees.add(reorderTestee2);

        final Reorder reorderTestee3 = createReorderTestee(3, 20, true, itemTestees.get(2));
        reorderTestees.add(reorderTestee3);

        return reorderTestees;
    }

    private Reorder createReorderTestee(final int reorderId, final int quantity, final boolean delivered, final Item itemTestee) {
        final Reorder testee = new Reorder();
        testee.setId(reorderId);
        testee.setQuantity(quantity);
        testee.setReorderDate(Timestamp.from(Instant.now()));
        testee.setDelivered((delivered) ? Timestamp.from(Instant.now()) : null);
        testee.setItemId(itemTestee.getId());
        testee.setItemByItemId(itemTestee);
        itemTestee.getReordersById().add(testee);
        return testee;
    }

    private List<Item> getItemTestees() {
        final List<Item> itemTestees = new ArrayList<>();

        final Item testee1 = new Item();
        testee1.setId(1);
        testee1.setName("testee1");
        testee1.setArtNr("testee1ArtNr");
        testee1.setPrice(7);
        testee1.setLocalStock(10);
        testee1.setMinLocalStock(5);
        testee1.setVirtualLocalStock(5);
        testee1.setReordersById(new ArrayList<>());
        itemTestees.add(testee1);

        final Item testee2 = new Item();
        testee2.setId(2);
        testee2.setName("testee2");
        testee2.setArtNr("testee2ArtNr");
        testee2.setPrice(14);
        testee2.setLocalStock(20);
        testee2.setMinLocalStock(10);
        testee2.setVirtualLocalStock(10);
        testee2.setReordersById(new ArrayList<>());
        itemTestees.add(testee2);

        final Item testee3 = new Item();
        testee3.setId(3);
        testee3.setName("testee3");
        testee3.setArtNr("testee3ArtNr");
        testee3.setPrice(28);
        testee3.setLocalStock(30);
        testee3.setMinLocalStock(20);
        testee3.setVirtualLocalStock(20);
        testee3.setReordersById(new ArrayList<>());
        itemTestees.add(testee3);

        return itemTestees;
    }
}
