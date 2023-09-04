package com.booklending.customer.service; 
 
import com.booklending.customer.exception.JwtException; 
import com.booklending.customer.utils.ApplicationConstants; 
import com.booklending.customer.utils.Claim; 
import org.junit.jupiter.api.Assertions; 
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.InjectMocks; 
import org.mockito.junit.jupiter.MockitoExtension; 
import org.springframework.test.util.ReflectionTestUtils; 
 
@ExtendWith(MockitoExtension.class) 
public class TokenServiceTest { 
 
    @InjectMocks 
    private TokenServiceImpl tokenService; 
 
    private static final String SECRET = "secret"; 
    private static final String SECRET_VALUE = "book_lend_MS"; 
    private static final String ISSUER = "issuer"; 
    private static final String ISSUER_VALUE = "book_lend"; 
    private final static Long CUSTOMER_ID = 1L; 
    private final static String EXPIRED_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJle" + 
            "HAiOjE2OTM0OTYwMDgsImlhdCI6MTY5MzQ5MzAwOCwiaXNzIjoiYm9va19sZW5kIiwiQ2xhaW0iOlsi" + 
            "Q3VzdG9tZXI6UHJvZmlsZVVwZGF0ZSJdLCJzdWIiOiIxIn0.SvCl3FS-CsTTRskXfTQyfG8ScfTra" + 
            "3vIigc4GsEpH5fW42e1onZGiVY4DkNzGG64MFLGtuZHfGwiixYREuovsw"; 
 
    private static final String UNAUTHORIZED_USER_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MzA1MDE4" 
            + "MjAsImlhdCI6MTYzMDUwMDAyMCwiaXNzIjoiSSBoYXRlIGFscGhhYmV0cyBidXQgSSBsb3ZlIiwiQ2xhaW0iOl" 
            + "siSU5HX0FDQ09VTlRfU0VSVklDRTpHRVRfQ1VTVE9NRVJfQUNDT1VOVF9ERVRBSUxTIiwiSU5HX0FDQ09VTlRfU0VSV" 
            + "klDRTpVUERBVEVfQ1VTVE9NRVJfREVUQUlMUyJdLCJzdWIiOiIxMDAifQ.Dt-AKl6AH" 
            + "VXnnmJEP-zLeZd8HqjEbWXFTvKqwPwcdezlCgq4gJ39bjoNSWUFMWJ0Z6rUsRko9_Gp5U0-4yluBQ"; 
 
    private String VALID_TOKEN = null; 
 
    @BeforeEach 
    public void before() { 
        ReflectionTestUtils.setField(tokenService, SECRET, SECRET_VALUE); 
        ReflectionTestUtils.setField(tokenService, ISSUER, ISSUER_VALUE); 
 
        VALID_TOKEN = tokenService.getToken(CUSTOMER_ID); 
    } 
 
    @Test 
    public void testGetToken() { 
        //When 
        String response = VALID_TOKEN; 
 
        //Then 
        Assertions.assertNotNull(response); 
    } 
 
    @Test 
    public void testValidateToken_Expired() { 
        Throwable thrown = Assertions.assertThrows( 
                JwtException.class, () -> tokenService.validateToken(EXPIRED_TOKEN, Claim.UPDATE_PROFILE)); 
        Assertions.assertEquals(ApplicationConstants.TOKEN_EXPIRED, thrown.getMessage()); 
    } 
 
    @Test 
    public void testValidateToken_UnauthorizedUser_Signature() { 
        Throwable thrown = Assertions.assertThrows( 
                JwtException.class, () -> tokenService.validateToken(UNAUTHORIZED_USER_TOKEN, Claim.UPDATE_PROFILE)); 
        Assertions.assertEquals(ApplicationConstants.UNAUTHORIZED_USER, thrown.getMessage()); 
    } 
 
    @Test 
    public void testValidateToken() { 
        //When 
        String response = tokenService.validateToken(VALID_TOKEN, Claim.UPDATE_PROFILE); 
 
        //Then 
        Assertions.assertNotNull(response); 
    } 
} 
