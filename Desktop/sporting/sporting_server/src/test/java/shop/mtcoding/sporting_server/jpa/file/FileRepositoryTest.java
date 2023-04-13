package shop.mtcoding.sporting_server.jpa.file;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.sporting_server.core.enums.field.etc.FileInfoSource;
import shop.mtcoding.sporting_server.core.enums.field.status.FileStatus;
import shop.mtcoding.sporting_server.core.enums.field.status.UserStatus;
import shop.mtcoding.sporting_server.modules.file.entity.File;
import shop.mtcoding.sporting_server.modules.file.repository.FileRepository;
import shop.mtcoding.sporting_server.modules.fileinfo.entity.FileInfo;

@DataJpaTest
@ComponentScan
@SpringJUnitConfig
@Transactional
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        em.createNativeQuery("ALTER TABLE file_tb ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        setUp("사진1", "www.sefri.codfk,", FileStatus.대기);
    }

    @Test
    @Transactional
    void selectAll() {
        List<File> files = fileRepository.findAll();
        Assertions.assertNotEquals(files.size(), 0);

        File file = files.get(0);
        Assertions.assertEquals(file.getFileName(), "사진1");
    }

    @Test
    void selectAndUpdate() {
        var optionalFile = this.fileRepository.findById(1L);

        if (optionalFile.isPresent()) {
            var result = optionalFile.get();
            Assertions.assertEquals(result.getFileName(), "사진1");

            var fileSource = "사진222";
            result.setFileName(fileSource);
            ;
            File merge = entityManager.merge(result);

            Assertions.assertEquals(merge.getFileName(), "사진222");
        } else {
            Assertions.assertNotNull(optionalFile.get());
        }
    }

    @Test
    void insertAndDelete() {
        File file = setUp("사진2", "www.geil.codfk,", FileStatus.승인);
        Optional<File> findFile = this.fileRepository.findById(file.getId());

        if (findFile.isPresent()) {
            var result = findFile.get();
            Assertions.assertEquals(result.getFileName(), "사진2");

            entityManager.remove(file);

            Optional<File> deleteFile = this.fileRepository.findById(file.getId());
            if (deleteFile.isPresent()) {
                Assertions.assertNull(deleteFile.get());
            }
        } else {
            Assertions.assertNotNull(findFile.get());
        }
    }

    private File setUp(String fileName, String fileUrl, FileStatus status) {

        File file = new File();

        file.setFileInfo(setUpFileInfo(FileInfoSource.코트사진));
        file.setFileName(fileName);
        file.setFileUrl(fileUrl);
        file.setStatus(status);

        return this.entityManager.persist(file);
    }

    private FileInfo setUpFileInfo(FileInfoSource type) {

        FileInfo fileInfo = new FileInfo();

        fileInfo.setType(type);

        return this.entityManager.persist(fileInfo);
    }
}
