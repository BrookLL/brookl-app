package com.liu.brookl.base.manager

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
inline fun <reified VB : ViewBinding> Activity.createViewBinding():VB {
    return  inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}
inline fun <reified VB : ViewBinding> Activity.inflate() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> Dialog.inflate() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as VB


inline fun <reified VB : ViewBinding> Fragment.bindView() =
    FragmentViewBindingDelegate(VB::class.java)

class FragmentViewBindingDelegate<VB : ViewBinding>(private val clazz: Class<VB>) :
    ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            binding =
                clazz.getMethod("bind", View::class.java).invoke(null, thisRef.requireView()) as VB
            thisRef.doOnDestroyView { binding = null }
        }
        return binding!!
    }


}

inline fun Fragment.doOnDestroyView(crossinline block: () -> Unit) =
    viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                block.invoke()
            }
        }

    })

inline fun <reified VB : ViewBinding> newBinding(parent: ViewGroup): VB {
    val method = VB::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
    return method.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
}

