/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author M
 */
public class XmlFileFilter extends FileFilter {

    protected String extension;
    protected String description;

    public XmlFileFilter(String ext, String descr) {
	this.extension = ext;
	this.description = descr;
    }

    @Override
    public boolean accept(File f) {
	if (f != null) {
	    if (f.isDirectory()) {
		return true;
	    }
	    String ext = getExtension(f);
	    if (ext == null) {
		return (extension.length() == 0);
	    }
	    return extension.equals(ext);
	}
	return false;
    }

    public String getExtension(File f) {
	if (f != null) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if (i > 0 && i < filename.length() - 1) {
		return filename.substring(i + 1).toLowerCase();
	    }
	}
	return null;
    }

    @Override
    public String getDescription() {
	return description;
    }
}
