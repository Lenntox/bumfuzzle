package ch.bumfuzzle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "test")
public class TestEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String name;

  @Column(nullable = false)
  private boolean active;

  public TestEntity() {
  }

  public TestEntity(final Long id, final String name, final boolean active) {
    this.id = id;
    this.name = name;
    this.active = active;
  }

  @Override
  public String toString() {
    return "TestEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", active=" + active +
        '}';
  }
}
