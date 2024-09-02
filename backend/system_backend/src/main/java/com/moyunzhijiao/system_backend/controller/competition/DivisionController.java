package com.moyunzhijiao.system_backend.controller.competition;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.moyunzhijiao.system_backend.common.Constants;
import com.moyunzhijiao.system_backend.common.Result;
import com.moyunzhijiao.system_backend.entiy.competition.Division;
import com.moyunzhijiao.system_backend.entiy.competition.DivisionRequirements;
import com.moyunzhijiao.system_backend.service.competition.DivisionRequirementsService;
import com.moyunzhijiao.system_backend.service.competition.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/backend/division")
public class DivisionController {
    @Autowired
    DivisionService divisionService;
    @Autowired
    DivisionRequirementsService divisionRequirementsService;
    @PutMapping("/delete")
    public Result deleteDivision(@RequestBody Map<String,String> r){
        String id = r.get("id");
        divisionService.removeById(id);
        return Result.success();
    }
    @GetMapping("/information")
    public Result getInformation(@RequestParam Integer divisionId){
        Division division = divisionService.getById(divisionId);
        DivisionRequirements divisionRequirements = divisionRequirementsService.getById(divisionId);
        if(divisionRequirements!=null){
            division.setRequirement(divisionRequirements.getRequirements());
        }
        return Result.success(division);
    }

}
