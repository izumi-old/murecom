package org.izumi.murecom.entity.output;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@JmixEntity
@Table(name = "MURECOM_SONG")
@Entity(name = "murecom_Song")
public class Song extends Output {
}
