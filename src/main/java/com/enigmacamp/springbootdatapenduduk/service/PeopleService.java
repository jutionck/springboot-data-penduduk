package com.enigmacamp.springbootdatapenduduk.service;

import com.enigmacamp.springbootdatapenduduk.model.dto.request.PeopleRequestDto;
import com.enigmacamp.springbootdatapenduduk.model.dto.request.QueryParamDto;
import com.enigmacamp.springbootdatapenduduk.model.entity.District;
import com.enigmacamp.springbootdatapenduduk.model.entity.People;
import com.enigmacamp.springbootdatapenduduk.model.entity.Province;
import com.enigmacamp.springbootdatapenduduk.model.entity.Regency;
import com.enigmacamp.springbootdatapenduduk.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final ProvinceService provinceService;
    private final RegencyService regencyService;
    private final DistrictService districtService;
    private final EmailService emailService;

    public People create(PeopleRequestDto payload) {
        validateServiceHours();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormatter.format(payload.getBod());

        Province province = provinceService.findById(payload.getProvinceId());
        Regency regency = regencyService.findById(payload.getRegencyId());
        District district = districtService.findById(payload.getDistrictId());

        String generatedNik = generateNik(
                province.getCode(),
                regency.getCode(),
                district.getCode(),
                formattedDate,
                payload.getGender());

        Optional<People> existingPerson = Optional.ofNullable(peopleRepository.findByNik(generatedNik));
        if (existingPerson.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NIK sudah terdaftar.");
        }

        People newPerson = People.builder()
                .name(payload.getName())
                .nik(generatedNik)
                .placeOfBirth(payload.getPlaceOfBirth())
                .bod(payload.getBod())
                .gender(payload.getGender())
                .province(province)
                .regency(regency)
                .district(district)
                .build();
        peopleRepository.save(newPerson);
        // Send mail here
        emailService.sendEmail("jutionck@gmail.com", newPerson.getNik());
        return newPerson;
    }

    public Page<People> findAll(QueryParamDto params) {
        Pageable pageable = PageRequest.of(Math.max(params.getPage() - 1, 0), params.getSize());
        Specification<People> specification = PeopleRepository.hasSearchQuery(params);
        return peopleRepository.findAll(specification, pageable);
    }

    public People findByNik(String nik) {
        return peopleRepository.findByNik(nik);
    }

    public List<People> findByProvince(Integer provinceId) {
        return peopleRepository.findByProvinceId(provinceId);
    }

    public List<People> findByRegency(Integer regencyId) {
        return peopleRepository.findByRegencyId(regencyId);
    }

    public List<People> findByDistrict(Integer districtId) {
        return peopleRepository.findByDistrictId(districtId);
    }

    public People findById(Integer id) {
        return peopleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "person not found"));
    }

    private void validateServiceHours() {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Layanan Pembuatan KTP hanya tersedia Senin - Jumat.");
        }

        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(14, 0);
        if (now.isBefore(start) || now.isAfter(end)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Layanan Pembuatan KTP sudah tutup.");
        }
    }

    private String generateNik(
            String provinceCode,
            String regencyCode,
            String districtCode,
            String birthDate,
            String gender) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(birthDate, formatter);

        String yearSuffix = String.valueOf(dateOfBirth.getYear()).substring(2);
        String month = String.format("%02d", dateOfBirth.getMonthValue());
        String day = String.format("%02d", dateOfBirth.getDayOfMonth());

        if (gender.equalsIgnoreCase("P")) {
            int dayInt = Integer.parseInt(day) + 40;
            day = String.format("%02d", dayInt);
        }

        Random rand = new Random();
        int last4DigitNumbers = rand.nextInt(1000);
        return provinceCode + regencyCode + districtCode + day + month + yearSuffix + String.format("%04d", last4DigitNumbers);
    }
}
