//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.12 at 06:59:29 PM IDT 
//


package com.monopoly.scheme;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for monetaryCard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="monetaryCard">
 *   &lt;complexContent>
 *     &lt;extension base="{}cardBase">
 *       &lt;attribute name="who" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="TREASURY"/>
 *             &lt;enumeration value="PLAYERS"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="sum" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "monetaryCard")
public class MonetaryCard
    extends CardBase
{

    @XmlAttribute(name = "who", required = true)
    protected String who;
    @XmlAttribute(name = "sum", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long sum;

    /**
     * Gets the value of the who property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWho() {
        return who;
    }

    /**
     * Sets the value of the who property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWho(String value) {
        this.who = value;
    }

    /**
     * Gets the value of the sum property.
     * 
     */
    public long getSum() {
        return sum;
    }

    /**
     * Sets the value of the sum property.
     * 
     */
    public void setSum(long value) {
        this.sum = value;
    }

}
