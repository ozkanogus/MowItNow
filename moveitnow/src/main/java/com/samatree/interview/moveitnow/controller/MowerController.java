package com.samatree.interview.moveitnow.controller;


import com.samatree.interview.moveitnow.model.MowRequest;
import com.samatree.interview.moveitnow.model.Mower;
import com.samatree.interview.moveitnow.service.MowerService;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api")
public class MowerController {
    private final MowerService mowerService;

    public MowerController(MowerService mowerService) {
        this.mowerService = mowerService;
    }

    @PostMapping("/mow")
    public List<Mower> mowItNow(@RequestBody MowRequest request){
       return mowerService.processCommands(request.getLawnField(), request.getMoveItCommandList());
    }

    @PostMapping("/mow-string")
    public String mowFromString(@RequestBody String input) {
        return mowerService.parsedRequestAndProcessCommands(input);
    }


}
