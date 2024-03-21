package com.tastetracker.storage;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService
{
    private final Logger logger = LoggerFactory.getLogger( FileStorageService.class );
    private final String fileStorageLocation;
    private final String imageStorageLocation;
    private final String categoryBannerStorageLocation;

    public FileStorageService( @Value ( "${app.storage.location}" ) String storageLocation )
    {
        this.fileStorageLocation = storageLocation + "/files/";
        this.imageStorageLocation = storageLocation + "/img/";
        this.categoryBannerStorageLocation = storageLocation + "/categorybanners/";
        Path fileStoragePath = Path.of( this.fileStorageLocation );
        Path imageStoragePath = Path.of( this.imageStorageLocation );
        Path categoryBannerStoragePath = Path.of( this.categoryBannerStorageLocation );

        prepareStorageDirectiories( fileStoragePath, imageStoragePath, categoryBannerStoragePath );
    }

    private void prepareStorageDirectiories( Path fileStoragePath,
                                             Path imageStoragePath,
                                             Path categoryBannerStoragePath )
    {
        try
        {
            if ( Files.notExists( fileStoragePath ) )
            {
                Files.createDirectories( fileStoragePath );
                logger.info( "File storage directory created %s".formatted( fileStoragePath.toAbsolutePath() ) );
            }

            if ( Files.notExists( imageStoragePath ) )
            {
                Files.createDirectories( imageStoragePath );
                logger.info( "Image storage directory created %s".formatted( imageStoragePath.toAbsolutePath() ) );
            }

            if ( Files.notExists( categoryBannerStoragePath ) )
            {
                Files.createDirectories( categoryBannerStoragePath );
                logger.info( "Image storage directory created %s".formatted( categoryBannerStoragePath.toAbsolutePath() ) );
            }
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( "Can't create storage directiories", e );
        }
    }


    public String saveImage( MultipartFile file )
    {
        return saveFile( file, imageStorageLocation );
    }

    public String saveFile( MultipartFile file )
    {
        return saveFile( file, fileStorageLocation );
    }

    public String saveCategoryBannerImage( MultipartFile file )
    {
        return saveFile( file, categoryBannerStorageLocation );
    }

    private String saveFile( MultipartFile file, String storageLocation )
    {
        Path filePath = createFilePath( file, storageLocation );

        try
        {
            Files.copy( file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING );
            return filePath.getFileName().toString();
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( e );
        }
    }

    private Path createFilePath( MultipartFile file, String storageLocation )
    {
        String originalFileName = file.getOriginalFilename();
        String fileBaseName = FilenameUtils.getBaseName( originalFileName );
        String fileExtension = FilenameUtils.getExtension( originalFileName );
        String completeFilename;
        Path filePath;
        int fileIndex = 0;

        do
        {
            completeFilename = fileBaseName + fileIndex + "." + fileExtension;
            filePath = Paths.get( storageLocation, completeFilename );
            fileIndex++;
        } while ( Files.exists( filePath ) );

        return filePath;
    }
}
