package ch.hslu.appe.fbs.remote.service.reorder;

import ch.hslu.appe.fbs.business.reorder.ReorderManager;
import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ReorderService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.Item;
import ch.hslu.appe.fbs.model.db.Reorder;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;
import ch.hslu.appe.fbs.wrapper.ReorderWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class ReorderServiceImplTest {

    private static final String LOCALHOST = "localhost";

    @Mock
    private ReorderManager reorderManager;

    @Mock
    private ClientHost clientHost;

    @Mock
    private UserSessionMap userSessionMap;

    private ReorderService reorderService;
    private List<ReorderDTO> reorderTestees;
    private UserDTO userTestee;

    @Before
    public void setUp() throws UserNotAuthorisedException {
        MockitoAnnotations.initMocks(this);
        this.reorderService = new ReorderServiceImpl(this.clientHost, this.userSessionMap, this.reorderManager);
        this.reorderTestees = getReorderTestees();
        this.userTestee = getUserTestee();
        when(this.clientHost.getHostAddress()).thenReturn(LOCALHOST);
        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.of(this.userTestee));
        when(this.reorderManager.getAllReorders(this.userTestee)).thenReturn(this.reorderTestees);
    }

    @Test
    public void markReorderAsDelivered_WhenValidReorderId_ThenMarkAssociatedReorderAsDelivered()
            throws RemoteException, UserNotAuthorisedException {
        final ReorderDTO reorderDTO = this.reorderTestees.get(0);

        doAnswer(invocationOnMock -> {
            final int reorderUpdateId = invocationOnMock.getArgument(0);
            final UserDTO user = invocationOnMock.getArgument(1);

            assertEquals(reorderDTO.getId(), reorderUpdateId);
            assertEquals(this.userTestee.getId(), user.getId());
            assertEquals(this.userTestee.getUserName(), user.getUserName());
            assertEquals(this.userTestee.getUserRole(), user.getUserRole());

            final ReorderDTO updatedReorderDTO = new ReorderDTO(reorderDTO.getId(), reorderDTO.getItem(), reorderDTO.getQuantity(),
                    reorderDTO.getReorderDate(), Timestamp.from(Instant.now()));

            this.reorderTestees.set(0, updatedReorderDTO);

            return null;
        }).when(this.reorderManager).markReorderAsDelivered(any(Integer.class), any(UserDTO.class));

        this.reorderService.markReorderAsDelivered(reorderDTO.getId());

        final ReorderDTO updatedReorder = this.reorderTestees.get(0);

        assertEquals(reorderDTO.getId(), updatedReorder.getId());
        assertEquals(reorderDTO.getItem(), updatedReorder.getItem());
        assertEquals(reorderDTO.getQuantity(), updatedReorder.getQuantity());
        assertEquals(reorderDTO.getReorderDate(), updatedReorder.getReorderDate());
        assertNotNull(updatedReorder.getDelivered());
    }

    @Test
    public void markReorderAsDelivered_WhenValidReorderIdButInvalidUserSession_ThenLeaveReorderUnchanged()
            throws RemoteException, UserNotAuthorisedException {
        final ReorderDTO reorderDTO = this.reorderTestees.get(0);

        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.empty());

        this.reorderService.markReorderAsDelivered(reorderDTO.getId());

        final ReorderDTO updatedReorder = this.reorderTestees.get(0);

        assertEquals(reorderDTO.getId(), updatedReorder.getId());
        assertEquals(reorderDTO.getItem(), updatedReorder.getItem());
        assertEquals(reorderDTO.getQuantity(), updatedReorder.getQuantity());
        assertEquals(reorderDTO.getReorderDate(), updatedReorder.getReorderDate());
        assertNull(updatedReorder.getDelivered());
    }

    @Test
    public void getAllReorders_WhenReordersExist_ThenReturnThemAll()
            throws RemoteException, UserNotAuthorisedException {
        final List<ReorderDTO> reorders = this.reorderService.getAllReorders();
        assertEquals(this.reorderTestees.size(), reorders.size());
    }

    @Test
    public void getAllReorders_WhenReordersExistButInvalidUserSession_ThenReturnEmptyList()
            throws RemoteException, UserNotAuthorisedException {
        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.empty());

        final List<ReorderDTO> reorders = this.reorderService.getAllReorders();

        assertTrue(reorders.isEmpty());
    }

    @Test
    public void getServiceName_WhenServiceNameExists_ThenReturnIt() throws RemoteException {
        final String serviceName = this.reorderService.getServiceName();
        assertEquals(RmiLookupTable.getReorderServiceName(), serviceName);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SYSADMIN.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private List<ReorderDTO> getReorderTestees() {
        final List<ReorderDTO> reorderTestees = new ArrayList<>();
        final ReorderWrapper reorderWrapper = new ReorderWrapper();
        final List<Item> itemTestees = getItemTestees();

        final Reorder reorderTestee1 = createReorderTestee(1, 10, false, itemTestees.get(0));
        reorderTestees.add(reorderWrapper.dtoFromEntity(reorderTestee1));

        final Reorder reorderTestee2 = createReorderTestee(2, 5, false, itemTestees.get(1));
        reorderTestees.add(reorderWrapper.dtoFromEntity(reorderTestee2));

        final Reorder reorderTestee3 = createReorderTestee(3, 20, true, itemTestees.get(2));
        reorderTestees.add(reorderWrapper.dtoFromEntity(reorderTestee3));

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
