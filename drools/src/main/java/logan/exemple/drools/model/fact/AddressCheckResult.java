package logan.exemple.drools.model.fact;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * 你的支持是我努力的最大动力！社区的建立离不开你的支持。
 * 此系列课程正在持续更新中，相关讨论QQ（593177274）已经建立，欢迎大家加入讨论。
 * 本人博客地址：http://blog.csdn.net/wo541075754
 */
public class AddressCheckResult {
    private final PropertyChangeSupport changes  = new PropertyChangeSupport( this );

    private boolean postCodeResult = false; // true:通过校验；false：未通过校验

    public boolean isPostCodeResult() {
        return postCodeResult;
    }

    public void setPostCodeResult(final boolean postCodeResult) {
        final boolean oldState = this.postCodeResult;
        this.postCodeResult = postCodeResult;
        this.changes.firePropertyChange( "postCodeResult",
                oldState,
                postCodeResult );
    }
    public void addPropertyChangeListener(final PropertyChangeListener l) {
        this.changes.addPropertyChangeListener( l );
    }

    public void removePropertyChangeListener(final PropertyChangeListener l) {
        this.changes.removePropertyChangeListener( l );
    }
}
