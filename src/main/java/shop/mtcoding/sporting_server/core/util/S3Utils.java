package shop.mtcoding.sporting_server.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.sporting_server.modules.file.entity.ProfileFile;

@RequiredArgsConstructor
public class S3Utils {

    private final AmazonS3Client amazonS3Client;

    public static String chooseCourtName(String sport) {
        String fileName = "";
        switch (sport) {
            case "야구":
                fileName = "Court/야구_Court.png";
                break;
            case "풋살":
                fileName = "Court/풋살장_Court.png";
                break;
            case "축구":
                fileName = "Court/축구_Court.png";
                break;
            case "볼링":
                fileName = "Court/볼링_Court.png";
                break;
            case "배구":
                fileName = "Court/배구_Court.png";
                break;
            case "탁구":
                fileName = "Court/탁구_Court.png";
                break;
            case "농구":
                fileName = "Court/농구_Court.png";
                break;
            case "테니스":
                fileName = "Court/테니스_Court.png";
                break;
            default:
                throw new IllegalArgumentException("Invalid keyword: " + sport);
        }
        return fileName;
    }

    public static String chooseStadiumName(String sport) {
        String fileName = "";
        switch (sport) {
            case "야구":
                fileName = "Stadium/야구_Stadium.png";
                break;
            case "풋살":
                fileName = "Stadium/풋살장_Stadium.png";
                break;
            case "축구":
                fileName = "Stadium/축구_Stadium.png";
                break;
            case "볼링":
                fileName = "Stadium/볼링_Stadium.png";
                break;
            case "배구":
                fileName = "Stadium/배구_Stadium.png";
                break;
            case "탁구":
                fileName = "Stadium/탁구_Stadium.png";
                break;
            case "농구":
                fileName = "Stadium/농구_Stadium.png";
                break;
            case "테니스":
                fileName = "Stadium/테니스_Stadium.png";
                break;
            default:
                throw new IllegalArgumentException("Invalid keyword: " + sport);
        }
        return fileName;
    }

    public static String chooseCourtUrl(String sport) {
        String url = "";
        switch (sport) {
            case "야구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EC%95%BC%EA%B5%AC_Court.png";
                break;
            case "풋살":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%ED%92%8B%EC%82%B4%EC%9E%A5_Court.png";
                break;
            case "축구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EC%B6%95%EA%B5%AC_Court.png";
                break;
            case "볼링":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%B3%BC%EB%A7%81_Court.png";
                break;
            case "배구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%B0%B0%EA%B5%AC_Court.png";
                break;
            case "탁구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%ED%83%81%EA%B5%AC_Court.png";
                break;
            case "농구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%86%8D%EA%B5%AC_Court.png";
                break;
            case "테니스":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%ED%85%8C%EB%8B%88%EC%8A%A4_Court.png";
                break;
            default:
                throw new IllegalArgumentException("Invalid keyword: " + sport);
        }
        return url;
    }

    public static String chooseStadiumUrl(String sport) {
        String url = "";
        switch (sport) {
            case "야구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EC%95%BC%EA%B5%AC_Stadium.png";
                break;
            case "풋살":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%ED%92%8B%EC%82%B4%EC%9E%A5_Stadium.png";
                break;
            case "축구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EC%B6%95%EA%B5%AC_Stadium.png";
                break;
            case "볼링":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%B3%BC%EB%A7%81_Stadium.png";
                break;
            case "배구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%B0%B0%EA%B5%AC_Stadium.png";
                break;
            case "탁구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%ED%83%81%EA%B5%AC_Stadium.png";
                break;
            case "농구":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%86%8D%EA%B5%AC_Stadium.png";
                break;
            case "테니스":
                url = "https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%ED%85%8C%EB%8B%88%EC%8A%A4_Stadium.png";
                break;
            default:
                throw new IllegalArgumentException("Invalid keyword: " + sport);
        }
        return url;
    }

    public static List<String> uploadFile(MultipartFile multipartFile, String keyword, String bucket,
            AmazonS3Client amazonS3Client) throws IOException {
        ObjectMetadata objectMetadata = S3Utils.makeObjectMetadata(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = S3Utils.makeStoreFileName(originalFilename);
        String key = S3Utils.branchFolder(keyword, storeFileName);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();

        List<String> nameAndUrl = new ArrayList<>();
        nameAndUrl.add(key);
        nameAndUrl.add(storeFileUrl);

        return nameAndUrl;
    }

    public static ObjectMetadata makeObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        return objectMetadata;
    };

    public static String makeStoreFileName(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);
        String storeFileName = UUID.randomUUID() + "." + ext;

        return storeFileName;
    };

    public static Boolean updateProfileCheck_Stadium(ProfileFile stadiumProfileFilePS, String fileBase64, String bucket,
            String staticRegion) throws IOException {

        Boolean check = false;

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(staticRegion).build();

        ObjectMetadata objectMetadata = s3Client.getObjectMetadata(bucket,
                stadiumProfileFilePS.getFileName());

        long stadiumDBFileSize = objectMetadata.getContentLength();

        MultipartFile multipartFile1 = BASE64DecodedMultipartFile
                .convertBase64ToMultipartFile(fileBase64);
        long stadiumDTOFileSize = multipartFile1.getSize();
        if (stadiumDBFileSize == stadiumDTOFileSize) {
            check = true;
        }

        return check;
    }

    public static Boolean updateProfileCheck_Company(ProfileFile companyProfileFilePS, String fileBase64, String bucket,
            String staticRegion) throws IOException {

        Boolean check = false;

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(staticRegion).build();

        ObjectMetadata objectMetadata = s3Client.getObjectMetadata(bucket,
                companyProfileFilePS.getFileName());

        long companyDBFileSize = objectMetadata.getContentLength();
        MultipartFile multipartFile1 = BASE64DecodedMultipartFile
                .convertBase64ToMultipartFile(fileBase64);
        long companyDTOFileSize = multipartFile1.getSize();
        if (companyDBFileSize == companyDTOFileSize) {
            check = true;
        }

        return check;
    }

    public static Boolean updateProfileCheck_Court(ProfileFile courtProfileFilePS, String fileBase64, String bucket,
            String staticRegion) throws IOException {

        Boolean check = false;

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(staticRegion).build();
        ObjectMetadata objectMetadata = s3Client.getObjectMetadata(bucket,
                courtProfileFilePS.getFileName());

        long stadiumDBFileSize = objectMetadata.getContentLength();
        MultipartFile multipartFile1 = BASE64DecodedMultipartFile
                .convertBase64ToMultipartFile(fileBase64);
        long stadiumDTOFileSize = multipartFile1.getSize();
        if (stadiumDBFileSize == stadiumDTOFileSize) {
            check = true;
        }

        return check;
    }

    public static String branchFolder(String keyword, String storeFileName) {
        String key;
        switch (keyword) {
            case "CompanyProfile":
                key = "CompanyProfile/" + storeFileName;
                break;
            case "Court":
                key = "Court/" + storeFileName;
                break;
            case "PlayerProfile":
                key = "PlayerProfile/" + storeFileName;
                break;
            case "Stadium":
                key = "Stadium/" + storeFileName;
                break;
            default:
                throw new IllegalArgumentException("Invalid keyword: " + keyword);
        }
        return key;
    }
}
