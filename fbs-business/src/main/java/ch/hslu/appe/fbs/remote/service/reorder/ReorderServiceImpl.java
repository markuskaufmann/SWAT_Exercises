package ch.hslu.appe.fbs.remote.service.reorder;

import ch.hslu.appe.fbs.business.reorder.ReorderManager;
import ch.hslu.appe.fbs.common.dto.ReorderDTO;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.rmi.ReorderService;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class ReorderServiceImpl implements ReorderService {

    private final ClientHost clientHost;
    private final UserSessionMap userSessionMap;

    private final ReorderManager reorderManager;

    public ReorderServiceImpl(final ClientHost clientHost, final UserSessionMap userSessionMap, final ReorderManager reorderManager) {
        this.clientHost = clientHost;
        this.userSessionMap = userSessionMap;
        this.reorderManager = reorderManager;
    }

    @Override
    public void markReorderAsDelivered(final int reorderId) throws RemoteException, UserNotAuthorisedException {
        final Optional<UserDTO> optUserDTO = this.userSessionMap.getUserSession(this.clientHost.getHostAddress());
        if(optUserDTO.isPresent()) {
            this.reorderManager.markReorderAsDelivered(reorderId, optUserDTO.get());
        }
    }

    @Override
    public List<ReorderDTO> getAllReorders() throws RemoteException, UserNotAuthorisedException {
        final Optional<UserDTO> optUserDTO = this.userSessionMap.getUserSession(this.clientHost.getHostAddress());
        if(optUserDTO.isPresent()) {
            this.reorderManager.getAllReorders(optUserDTO.get());
        }
        return Collections.emptyList();
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getReorderServiceName();
    }
}
