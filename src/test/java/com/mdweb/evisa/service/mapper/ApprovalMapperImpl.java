package com.mdweb.evisa.service.mapper;

import com.mdweb.evisa.domain.Approval;
import com.mdweb.evisa.domain.Request;
import com.mdweb.evisa.domain.User;
import com.mdweb.evisa.service.dto.ApprovalDTO;
import com.mdweb.evisa.service.dto.RequestDTO;
import com.mdweb.evisa.service.dto.UserDTO;
import java.util.List;

public class ApprovalMapperImpl implements ApprovalMapper {

    @Override
    public List<Approval> toEntity(List<ApprovalDTO> dtoList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ApprovalDTO> toDto(List<Approval> entityList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void partialUpdate(Approval entity, ApprovalDTO dto) {
        // TODO Auto-generated method stub

    }

    @Override
    public ApprovalDTO toDto(Approval s) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Approval toEntity(ApprovalDTO approvalDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RequestDTO toDtoRequestId(Request request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDTO toDtoUserLogin(User user) {
        // TODO Auto-generated method stub
        return null;
    }
}
