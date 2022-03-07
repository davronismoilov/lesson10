package uz.pdp.relation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.relation.entity.Student;
import uz.pdp.relation.payload.StudentDTO;
import uz.pdp.relation.repository.AddressRepository;
import uz.pdp.relation.repository.GroupRepository;
import uz.pdp.relation.repository.StudentRepository;

@RestController
@RequestMapping("/student")
public class StudentController {
    final
    StudentRepository studentRepository;
    final
    AddressRepository addressRepository;
    final
    GroupRepository groupRepository;

    public StudentController(StudentRepository studentRepository, AddressRepository addressRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.groupRepository = groupRepository;
    }

    //1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {

        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAll(pageable);
    }

    //2. UNIVERSITY
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
    }

    //3. FACULTY DEKANAT
    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student> getStudentListForFaculty(@PathVariable Integer facultyId,
                                                  @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroup_Faculty_Id(facultyId, pageable);
    }

    //4. GROUP OWNER
    @GetMapping("/forFaculty/{groupId}")
    public Page<Student> getStudentListForGroup(@PathVariable Integer groupId,
                                                @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGroupId(groupId, pageable);
    }

    @PostMapping
    public String addStudent(@RequestBody StudentDTO studentDTO) {
        studentRepository.save(new Student(
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                addressRepository.findById(studentDTO.getAddressId()).orElse(null),
                groupRepository.findById(studentDTO.getGroupId()).orElse(null), null));
        return "Student added";
    }

    @PutMapping("/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDTO studentDTO) {
        if (!studentRepository.existsById(id)) return "STUDENT NOT FOUND";
        studentRepository.save(new Student(
                id,
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                addressRepository.findById(studentDTO.getAddressId()).orElse(null),
                groupRepository.findById(studentDTO.getGroupId()).orElse(null), null));
        return "Student updated";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        studentRepository.findById(id).ifPresent(studentRepository::delete);
        return "STUDENT DELETED";
    }


}
