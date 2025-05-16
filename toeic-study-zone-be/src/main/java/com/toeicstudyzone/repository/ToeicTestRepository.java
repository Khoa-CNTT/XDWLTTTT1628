    package com.toeicstudyzone.repository;

    import com.toeicstudyzone.entity.ToeicTest;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

    import java.util.List;

    public interface ToeicTestRepository extends JpaRepository<ToeicTest, Long> {
        @Query("SELECT t FROM ToeicTest t JOIN FETCH t.testYear ty")
        List<ToeicTest> findAll();

        @Query("SELECT t FROM ToeicTest t JOIN FETCH t.testYear ty WHERE t.testYear.id = :yearId")
        List<ToeicTest> findByTestYearId(Long yearId);
    }