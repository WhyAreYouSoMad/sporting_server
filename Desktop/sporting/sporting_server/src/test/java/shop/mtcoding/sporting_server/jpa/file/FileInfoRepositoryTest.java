package shop.mtcoding.sporting_server.jpa.file;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;
import shop.mtcoding.sporting_server.modules.fileinfo.repository.FileInfoRepository;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Transactional
public class FileInfoRepositoryTest {

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE file_info_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();

        setUp(FileInfoSource.코트사진);
    }

    @Test
    @Transactional
    void selectAll() {
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        Assertions.assertNotEquals(fileInfoList.size(), 0);

        FileInfo fileInfo = fileInfoList.get(0);
        Assertions.assertEquals(fileInfo.getType(), FileInfoSource.코트사진);
    }

    @Test
    void selectAndUpdate() {
        var optionalFileInfo = this.fileInfoRepository.findById(1L);

        if (optionalFileInfo.isPresent()) {
            var result = optionalFileInfo.get();
            Assertions.assertEquals(result.getType(), FileInfoSource.코트사진);

            var fileInfoSource = FileInfoSource.경기장사진;
            result.setType(fileInfoSource);
            ;
            FileInfo merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getType(), FileInfoSource.경기장사진);
        } else {
            Assertions.assertNotNull(optionalFileInfo.get());
        }
    }

    @Test
    void insertAndDelete() {
        FileInfo fileInfo = setUp(FileInfoSource.기업프로필);
        Optional<FileInfo> findFileInfo = this.fileInfoRepository.findById(fileInfo.getId());

        if (findFileInfo.isPresent()) {
            var result = findFileInfo.get();
            Assertions.assertEquals(result.getType(), FileInfoSource.기업프로필);

            entityManager.remove(fileInfo);

            Optional<FileInfo> deletefileInfo = this.fileInfoRepository.findById(fileInfo.getId());
            if (deletefileInfo.isPresent()) {
                Assertions.assertNull(deletefileInfo.get());
            }
        } else {
            Assertions.assertNotNull(findFileInfo.get());
        }
    }

    private FileInfo setUp(FileInfoSource type) {

        FileInfo fileInfo = new FileInfo();

        fileInfo.setType(type);

        return this.entityManager.persist(fileInfo);
    }
}
