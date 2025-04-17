package com.demo.brandbacks.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.brandbacks.constants.Constants;
import com.demo.brandbacks.dto.OrderListDto;
import com.demo.brandbacks.dto.OrderRequest;
import com.demo.brandbacks.dto.OrderResponse;
import com.demo.brandbacks.model.Order;
import com.demo.brandbacks.model.Product;
import com.demo.brandbacks.repository.OrderRepository;
import com.demo.brandbacks.repository.ProductRepository;
import com.demo.brandbacks.security.UserPrincipal;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	QrCodeService qrcodeService;
	
	@Override
	public OrderResponse saveOrder(OrderRequest orderRequest, UserPrincipal userDetails) {
		try {
			  Order order = new Order();
		        order.setAddress(orderRequest.getAddress());
		        order.setProductId(orderRequest.getProductId());
		        order.setQuantity(orderRequest.getQuantity());
		        order.setCashbackAmount(orderRequest.getCashbackAmount());
		        order.setTotalAmount(calculateTotal(orderRequest));
		        order.setUserId(userDetails.getId());
		        order.setCreatedAt(Instant.now());
		        order.setUpdatedAt(Instant.now());
		        order.setStatus(Constants.OrderStatus.Pending.name());
		        Order savedOrder = orderRepository.save(order);

		        OrderResponse response = new OrderResponse();
		        response.setOrderId(savedOrder.getId());
		        response.setStatus("SUCCESS");
		        response.setTotalAmount(savedOrder.getTotalAmount());

		        return response;
		}catch(Exception exp) {
			exp.printStackTrace();
			return null;
		}
	}
	
	
	  private double calculateTotal(OrderRequest orderRequest) {
	        // Add logic to calculate total amount based on quantity and product price
	        // For example:
	        return orderRequest.getQuantity() *  orderRequest.getCashbackAmount(); // Assuming price per item is 100
	    }


	@Override
	public List<OrderListDto> getAll(UserPrincipal userDetails) {
		// TODO Auto-generated method stub
		List<Order> orders = orderRepository.findByUserId(userDetails.getId());
		
		
		
		return orders.stream().map((order)->{
            OrderListDto response = new OrderListDto();
            response.setId(order.getId());
            response.setAddress(order.getAddress());
            response.setQuantity(order.getQuantity());
            response.setCashbackAmount(order.getCashbackAmount());
            response.setTotalAmount(order.getTotalAmount());
            response.setUserId(order.getUserId());
            response.setStatus(order.getStatus());

            // Fetch product name
            Product product = productRepository.findById(order.getProductId()).orElse(null);
            response.setProductName(product != null ? product.getName() : "Unknown Product");

            return response;
			
		}).collect(Collectors.toList());
	}

	

	public ByteArrayOutputStream generateQrCodesPdf(List<String> qrDataList) throws Exception {
		 // Create a new PDF document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Create a content stream to add content to the PDF
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Start a new text block for text content
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(100, 700); // Set starting position for text

        // Write text content (if any) here
        contentStream.showText("Order Details");
        contentStream.endText();

        // Now, start adding the QR codes (images) outside of the text block
        contentStream.close(); // End the current content stream, as we need to start drawing images

        // Create a new content stream specifically for images
        contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

        // Loop through the QR code data and add them to the PDF
        float yPosition = 650; // Starting Y position for the first QR code
        for (String data : qrDataList) {
            // Generate the QR code image using ZXing
            BufferedImage qrImage = qrcodeService.generateQrCodeImage(data);

            // Convert the BufferedImage to a PDImageXObject
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", byteArrayOutputStream);
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, byteArrayOutputStream.toByteArray(), "QR Code");

            // Draw the image onto the PDF at a specific position
            contentStream.drawImage(pdImage, 100, yPosition); // Adjust position as needed
            yPosition -= 120; // Adjust for next QR code

            // Optional: If you want a text below the QR code, you can add another text block for each QR code
        }

        contentStream.close(); // End the content stream for the images

        // Convert the document to a byte array
        ByteArrayOutputStream finalOutputStream = new ByteArrayOutputStream();
        document.save(finalOutputStream);
        document.close();

        // Return the byte array containing the PDF data
        return finalOutputStream;    }
	
}
