package com.graduate.odondong.controller;

import com.graduate.odondong.domain.Bathroom;
import com.graduate.odondong.dto.BathroomRequestDto;
import com.graduate.odondong.dto.CoordinateInfoDto;
import com.graduate.odondong.service.BathroomService.BathroomService;
import com.graduate.odondong.util.BaseException;
import com.graduate.odondong.util.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.graduate.odondong.util.ErrorLogWriter.writeExceptionWithRequest;


@Controller
@RestController
@RequiredArgsConstructor
public class BathroomController {

    private final BathroomService bathroomService;

    @ResponseBody
    @GetMapping("/admin/bathroom/all")
    public List<Bathroom> AllBathroomList () {
        return bathroomService.bathroomList();
    }

    @ResponseBody
    @GetMapping("/admin/bathroom/registered")
    public List<Bathroom> RegisterBathroomList () {
        return bathroomService.RegisterBathroomList();
    }

    @ResponseBody
    @PostMapping("/api/bathroom/add")
    public BaseResponse<String> RegisterBathroomRequest (HttpServletRequest request, @RequestBody BathroomRequestDto bathroomRequestDto) {
        try {
            return new BaseResponse<String>(bathroomService.RegisterBathroomRequest(bathroomRequestDto));
        }
        catch (BaseException e) {
            writeExceptionWithRequest(e, request);
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/admin/bathroom/not-registered")
    public String NotRegisterBathroomList (Model model) {
        List<Bathroom> bathrooms = bathroomService.NotRegisterBathroomList();
        model.addAttribute("bathrooms", bathrooms);
        return "register";
    }

    @PostMapping("/admin/bathroom/register")
    public String RegisterBathroom(@RequestParam("id") Long id){
        bathroomService.UpdateBathroom(id);
        return "redirect:/not-register-bathroom";
    }

    @DeleteMapping("/admin/bathroom/delete")
    @ResponseBody
    public String DeleteBathroom(@RequestParam("id") Long id){
        bathroomService.DeleteBathroom(id);
        return "Delete";
    }
    @ResponseBody
    @GetMapping("/api/bathroom/address")
    public CoordinateInfoDto AllAddressInfo(@RequestParam("longitude") Double x, @RequestParam("latitude") Double y){
        return bathroomService.getAddressByCoordinate(x, y);
    }

    @ResponseBody
    @GetMapping("/api/bathroom/list")
    public List<Bathroom> get1kmBathroom(@RequestParam("longitude") Double x, @RequestParam("latitude") Double y) {
        return bathroomService.get1kmByLongitudeLatitude(y, x);
    }

}
