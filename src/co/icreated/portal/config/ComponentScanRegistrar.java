package co.icreated.portal.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Registers Spring components discovered by the OSGi-aware ClassGraph scan in {@link Activator}.
 * Replaces the previous reflective mutation of {@code @Import} (which broke on JDK 17+ strict
 * module access).
 */
public class ComponentScanRegistrar implements ImportBeanDefinitionRegistrar {

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
      BeanDefinitionRegistry registry) {
    Class<?>[] components = Activator.getSpringComponents();
    if (components.length == 0) {
      return;
    }
    new AnnotatedBeanDefinitionReader(registry).register(components);
  }
}
