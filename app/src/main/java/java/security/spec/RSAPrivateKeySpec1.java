/*     */ package java.security.spec;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RSAPrivateKeySpec1
/*     */   implements KeySpec
/*     */ {
/*     */   private final BigInteger modulus;
/*     */   private final BigInteger privateExponent;
/*     */   private final AlgorithmParameterSpec params;
/*     */   
/*     */   public RSAPrivateKeySpec1(BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
/*  57 */     this(paramBigInteger1, paramBigInteger2, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSAPrivateKeySpec1(BigInteger paramBigInteger1, BigInteger paramBigInteger2, AlgorithmParameterSpec paramAlgorithmParameterSpec) {
/*  70 */     this.modulus = paramBigInteger1;
/*  71 */     this.privateExponent = paramBigInteger2;
/*  72 */     this.params = paramAlgorithmParameterSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getModulus() {
/*  81 */     return this.modulus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getPrivateExponent() {
/*  90 */     return this.privateExponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmParameterSpec getParams() {
/* 101 */     return this.params;
/*     */   }
/*     */ }


/* Location:              C:\Program Files\Java\jdk1.8.0_271\jre\lib\rt.jar!\java\security\spec\RSAPrivateKeySpec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */