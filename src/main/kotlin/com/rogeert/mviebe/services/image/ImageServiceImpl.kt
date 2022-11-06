package com.rogeert.mviebe.services.image

import com.rogeert.mviebe.models.entities.Image
import com.rogeert.mviebe.repositories.ImageRepository
import com.rogeert.mviebe.util.Response
import org.imgscalr.Scalr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import javax.imageio.ImageIO

@Service
class ImageServiceImpl: ImageService{

    @Autowired
    lateinit var imageRepository: ImageRepository

    @Value("\${BASE_URL}")
    var BASE_URL = ""

    override fun saveImage(data: MultipartFile): Response<Image> {
        val response = Response<Image>()

        if(data.isEmpty){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("File data is missing")

            return response
        }

        val newImage = Image()
        newImage.content = resizeImage(data.bytes,720)

        response.success = true
        response.status = HttpStatus.OK
        response.messages.add("Image saved successfully.")
        response.data = imageRepository.save(newImage)

        return response
    }

    @Throws(IOException::class)
    private fun resizeImage(imageBytes: ByteArray, newWidth: Int): ByteArray? {
        val inputStream: InputStream = ByteArrayInputStream(imageBytes)
        val bufferedImage = ImageIO.read(inputStream)
        val baos = ByteArrayOutputStream()
        ImageIO.write(Scalr.resize(bufferedImage, newWidth), "png", baos)
        return baos.toByteArray()
    }

    override fun getImage(id: Long): ByteArray? {
        return imageRepository.findById(id).get().content
    }

    override fun getAllImages(): Response<List<String>> {
        val response = Response<List<String>>()

        response.messages.add("All images")
        response.success = true
        response.status = HttpStatus.OK
        response.data = imageRepository.findAll().toList().map { image -> image.getLink(BASE_URL) }

        return response
    }

    override fun deleteImage(id:Long): Response<String> {
        val response = Response<String>()

        val image = imageRepository.findById(id)

        if(image.isEmpty){
            response.status = HttpStatus.BAD_REQUEST
            response.success = false
            response.messages.add("Image not found.")
            return response
        }else{
            imageRepository.deleteById(id)
            response.status = HttpStatus.OK
            response.success = true
            response.messages.add("Image deleted.")
            response.data = "Deleted."
            return response
        }
    }
}