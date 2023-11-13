package org.emmek.beu2w3d1.controller;

import org.emmek.beu2w3d1.entities.Device;
import org.emmek.beu2w3d1.exception.BadRequestException;
import org.emmek.beu2w3d1.exception.NotFoundException;
import org.emmek.beu2w3d1.payloads.DevicePostDTO;
import org.emmek.beu2w3d1.payloads.DevicePutDTO;
import org.emmek.beu2w3d1.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("")
    public Page<Device> getDevices(@RequestParam(defaultValue = "0") long userId,
                                   @RequestParam(defaultValue = "") String category,
                                   @RequestParam(defaultValue = "") String state,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sort) {
        return deviceService.getDevices(userId, category, state, page, size, sort);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Device postDevices(@RequestBody @Validated DevicePostDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return deviceService.save(body);
        }
    }

    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable long id) {
        try {
            return deviceService.findById(id);
        } catch (Exception e) {
            throw new NotFoundException(id);
        }
    }

    @PutMapping("/{id}")
    public Device findByIdAndUpdate(@PathVariable long id, @RequestBody @Validated DevicePutDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return deviceService.findByIdAndUpdate(id, body);
        }
    }

    @DeleteMapping("/{id}")
    public void findByIdAndDelete(@PathVariable long id) {
        try {
            deviceService.findByIdAndDelete(id);
        } catch (Exception e) {
            throw new NotFoundException(id);
        }
    }

    @GetMapping("/{deviceId}/assign/{userId}")
    public Device assignUser(@PathVariable long deviceId, @PathVariable long userId) {
        return deviceService.assignUser(deviceId, userId);
    }

    @GetMapping("/{deviceId}/unassign")
    public Device unassignUser(@PathVariable long deviceId) {
        return deviceService.unassignUser(deviceId);
    }

    @GetMapping("{id}/putinmaintenance")
    public Device putInMaintenance(@PathVariable long id) {
        return deviceService.putInMaintenance(id);
    }

    @GetMapping("{id}/setavailable")
    public Device putInAvailable(@PathVariable long id) {
        return deviceService.setAvailable(id);
    }

    @GetMapping("{id}/setdisused")
    public Device putInDisused(@PathVariable long id) {
        return deviceService.putInDisused(id);
    }


}
