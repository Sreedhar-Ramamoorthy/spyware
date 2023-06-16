package org.rifluxyss.javadev.harddrivescanner.commons.gui.datparser;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;

public class IndexDatParser {
	final static int BLOCK_SIZE = 0x80;

	public static ArrayList<String> GetListOfIndexFiles(String folderPath) {
		ArrayList<String> datFiles = new ArrayList<String>();
		GetIndexFiles(folderPath, datFiles);
		return datFiles;
	}

	static void GetIndexFiles(String folderPath, ArrayList<String> datFiles) {
		File[] files = new File(folderPath).listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.getName().equals("index.dat")) {
				datFiles.add(file.getAbsolutePath());
			}
		}

		File[] folders = new File(folderPath).listFiles();
		for (int i = 0; i < folders.length; i++) {
			File file = folders[i];
			if (file.isDirectory()) {
				GetIndexFiles(file.getAbsolutePath(), datFiles);
			}
		}

	}

	public static ParseResult Parse(String filePath) throws Exception {
		ParseResult result = new ParseResult();
		result.Entries = new IndexEntries();

		if (!new File(filePath).exists()) {
			throw new Exception("Data file does not exist");
		}

		try {
			// Try to open the file.
			
			RandomAccessFile fs = null;
			try {
				fs = new RandomAccessFile(filePath, "rw");
			} catch (Exception ioEx) {
				throw new Exception("failed to open file " + filePath, ioEx);
			}
			int bytesRead = 0;
			fs.seek(0);
			// fs.Position = 0;
			//int bytesRead = 0;
			
			byte[] buff = ReadData(fs, 4, 0x18, bytesRead);
			double fileVer = Double.parseDouble(new String(buff, "UTF8"));
			result.Entries.FileVersion = String.format("{0}", fileVer);
			buff = ReadData(fs, 4, 0x1C, bytesRead);
			int fileSize = BytesToInt(buff);
			// System.out.println("File Size: {0} => " + fileSize);

			// Read hash table location offset.
			buff = ReadData(fs, 4, 0x20, bytesRead);
			int hashOffset = BytesToInt(buff);
			int nextHashOffset = 0;
			int hashSize = 0;
			int curRecOffset = 0;
			char[] type = new char[5];
			while (hashOffset != 0) {
				buff = ReadData(fs, 4, hashOffset + 8, bytesRead);
				nextHashOffset = BytesToInt(buff);

				buff = ReadData(fs, 4, hashOffset + 4, bytesRead);
				hashSize = BytesToInt(buff) * BLOCK_SIZE;
				for (int offset = hashOffset + 16; offset < hashOffset
						+ hashSize; offset += 8) {
					byte[] hashrecflagsstr = ReadData(fs, 4, offset, bytesRead);
					int hashRecFlags = BytesToInt(hashrecflagsstr);

					buff = ReadData(fs, 4, offset + 4, bytesRead);
					curRecOffset = BytesToInt(buff);
					if (hashrecflagsstr[0] != 0x03 && curRecOffset != 0xBADF00D) {
						if (curRecOffset >= 0) {
							buff = ReadData(fs, 4, curRecOffset, bytesRead);
							for (int i = 0; i < 4; i++) {
								type[i] = (char) buff[i];
							}
							type[4] = '\0';

							if (type[0] == 'R' && type[1] == 'E'
									&& type[2] == 'D' && type[3] == 'R') {
								// Parse REDR entry
								RedirectEntry redr = ParseREDR(fs,
										curRecOffset, fileSize, fileVer);
								if (null != redr) {
									result.Entries.RedirectEntries.add(redr);
								} else {
									result.Entries.Skipped++;
								}
							} else if ((type[0] == 'U' && type[1] == 'R' && type[2] == 'L')
									|| (type[0] == 'L' && type[1] == 'E'
											&& type[2] == 'A' && type[3] == 'K')) {
								// Parse URL/LEAK record.
								UrlEntry urlEntry = ParseURL(fs, curRecOffset,
										fileSize, fileVer);
								if (null != urlEntry) {
									result.Entries.UrlEntries.add(urlEntry);
								} else {
									result.Entries.Skipped++;
								}
							} else {
								// Parse unknown record
								ParseUnknown();
							}
						}
					}
				}
				hashOffset = nextHashOffset;
			}
		} catch (IOException ioEx) {
			result.Status = 404;
			result.Message = ioEx.getMessage();
		} catch (Exception ex) {
			result.Status = 500;
			result.Message = ex.getMessage();
		} finally {
			// System.out.println("!Done!");
		}

		return result;
	}

	static RedirectEntry ParseREDR(RandomAccessFile fs, int curRecOffset,
			int fileSize, double fileVer) {
		RedirectEntry redirEntry = new RedirectEntry();
		try {

			int bytesRead = 0;
			byte[] buff = ReadData(fs, 4, curRecOffset + 4, bytesRead);
			int recLen = BytesToInt(buff) * BLOCK_SIZE;

			char[] url = new char[recLen + 1];
			int i = 0;
			buff = ReadData(fs, 1, curRecOffset + 0x10, bytesRead);
			while (buff[0] != '\0' && curRecOffset + 0x10 + i + 1 < fileSize) {
				url[i] = (char) buff[0];
				buff = ReadData(fs, 1, curRecOffset + 0x10 + i + 1, bytesRead);
				i++;
			}
			url[i] = '\0';
			redirEntry.Url = new String(url, 0, i);
		} catch (Exception ex) {
			redirEntry = null;
		}
		return redirEntry;
	}
	/*
	public static String toHex(byte[] bytes) {
		BigInteger bi = new BigInteger(1, bytes);
		return String.format("%d" + (bytes.length << 1) + "X", bi);
	}
	*/
	static UrlEntry ParseURL(RandomAccessFile fs, int curRecOffset,
			int fileSize, double fileVer) {
		UrlEntry urlEntry = new UrlEntry();
		try {
			int bytesRead = 0;
			byte[] buff = ReadData(fs, 4, curRecOffset + 4, bytesRead);
			int recLen = BytesToInt(buff) * BLOCK_SIZE;

			buff = ReadData(fs, 8, curRecOffset + 8, bytesRead);
			long modTime = WindowsTimeToUnix(buff);
			try {
				long lastModTime = ConvertFromUnixTimestamp(modTime);
				urlEntry.LastModifiedTime = lastModTime;
			} catch (Exception e) {
				e.printStackTrace();
			}

			buff = ReadData(fs, 8, curRecOffset + 16, bytesRead);
			long accessTime = WindowsTimeToUnix(buff);
			//System.out.println("accessTime = " + accessTime);
			try {
				long lastAccessTime = ConvertFromUnixTimestamp(accessTime);
				urlEntry.LastAccessTime = lastAccessTime;
				// System.out.println("lastAccessTime = " + lastAccessTime);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (fileVer >= 5) {
				buff = ReadData(fs, 1, curRecOffset + 0x34, bytesRead);
			} else {
				buff = ReadData(fs, 1, curRecOffset + 0x38, bytesRead);
			}

			char[] url = new char[recLen * 2 + 1];
			int i = 0;
			byte urlOffset = buff[0];
			try {
				buff = ReadData(fs, 1, curRecOffset + urlOffset, bytesRead);
				while (buff[0] != '\0'
						&& curRecOffset + urlOffset + i + 1 < fileSize) {
					url[i] = (char) buff[0];
					buff = ReadData(fs, 1, curRecOffset + urlOffset + i + 1,
							bytesRead);
					i++;
				}
				url[i] = '\0';
			} catch (Exception ex) {
			}
			if (i > 0) {
				urlEntry.Url = new String(url, 0, i);
			}

			char[] fileName = new char[recLen * 2 + 1];
			if (fileVer >= 5) {
				buff = ReadData(fs, 4, curRecOffset + 0x3C, bytesRead);
			} else {
				buff = ReadData(fs, 4, curRecOffset + 0x40, bytesRead);
			}

			int fileNameOffset = BytesToInt(buff);
			i = 0;
			if (fileNameOffset > curRecOffset + 0x3C) {
				buff = ReadData(fs, 1, fileNameOffset, bytesRead);
				while (buff[0] != '\0' && fileNameOffset + i + 1 < fileSize) {
					fileName[i] = (char) buff[0];
					buff = ReadData(fs, 1, fileNameOffset + i + 1, bytesRead);
					i++;
				}
			}
			fileName[i] = '\0';
			if (i > 0) {
				urlEntry.FileName = new String(fileName, 0, i);
			}

			if (fileVer >= 5.2) {
				buff = ReadData(fs, 1, curRecOffset + 0x38, bytesRead);
			} else if (fileVer >= 5) {
				buff = ReadData(fs, 1, curRecOffset + 0x39, bytesRead);
			} else {
				buff = ReadData(fs, 1, curRecOffset + 0x3C, bytesRead);
			}

			byte[] dirName = new byte[9];
			int dirNameOffset = buff[0];

			if (0x50 + (12 * dirNameOffset) + 8 < fileSize) {
				dirName = ReadData(fs, 8, 0x50 + (12 * dirNameOffset),
						bytesRead);
				// TODO urlEntry.DirectoryName =
				// ASCIIEncoding.ASCII.GetString(dirName);
				urlEntry.DirectoryName = new String(dirName, "US-ASCII");
			} else {
				dirName[0] = 0;
			}

			char[] httpHeaders = new char[recLen * 2 + 1];
			if (fileVer >= 5) {
				buff = ReadData(fs, 4, curRecOffset + 0x44, bytesRead);
			} else {
				buff = ReadData(fs, 4, curRecOffset + 0x48, bytesRead);
			}

			int httpHeadersOffset = BytesToInt(buff) + curRecOffset;

			i = 0;
			if (httpHeadersOffset > curRecOffset + 0x44) {
				buff = ReadData(fs, 1, httpHeadersOffset, bytesRead);
				while (buff[0] != '\0'
						&& httpHeadersOffset + i + 1 < curRecOffset + recLen
						&& httpHeadersOffset + i + 1 < fileSize) {
					httpHeaders[i] = (char) buff[0];
					buff = ReadData(fs, 1, httpHeadersOffset + i + 1, bytesRead);
					i++;
				}
			}

			httpHeaders[i] = '\0';
			if (i > 0) {
				urlEntry.Headers = new String(httpHeaders, 0, i);
			}
		} catch (Exception ex) {
			urlEntry = null;
		}
		return urlEntry;
	}

	static void ParseUnknown() {
	}

	static byte[] ReadData(RandomAccessFile fs, int bytesToRead, int offset,
			int _bytesRead) throws IOException {
		int bytesRead=0;
		byte[] buff = new byte[bytesToRead];
		try {
			fs.seek(offset);
			_bytesRead = fs.read(buff, 0, bytesToRead);
		} catch (IOException ioEx) {
			throw ioEx;
		}
		bytesRead = _bytesRead;
		return buff;
	}

	static Calendar ConvertFromUnixTimestamp1(double timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// System.out.println("cal = " + cal.getTime());
		cal.add(Calendar.SECOND, (int) timestamp);
		// System.out.println("cal = " + cal.getTime());

		return cal;
		// Date origin = new Date(1970, 1, 1, 0, 0, 0, 0);
		// return origin.AddSeconds(timestamp);
	}

	static long ConvertFromUnixTimestamp(double timestamp) {
		return (long) timestamp;
	}

	static long WindowsTimeToUnix(byte[] val) {
		long low, high;
		double dbl;
		long total;

		byte[] fourbytes = new byte[4];

		fourbytes[0] = val[0];
		fourbytes[1] = val[1];
		fourbytes[2] = val[2];
		fourbytes[3] = val[3];

		low = BytesToInt(fourbytes);

		fourbytes[0] = val[4];
		fourbytes[1] = val[5];
		fourbytes[2] = val[6];
		fourbytes[3] = val[7];

		high = BytesToInt(fourbytes);

		dbl = (high) * (Math.pow(2, 32));
		dbl += (low);

		if (dbl == 0) {
			return 0;
		}

		dbl *= 1.0e-7;
		dbl -= 11644473600D;

		total = (long) dbl;

		return total;
	}

	public static int BytesToInt2(byte[] b) {
		return byteArrayToInt(b, 0);
	}

	
	public static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	static int BytesToInt(byte[] buff) {
		int retVal = 0;
		for (int i = 0; i < buff.length; i++) {
			int val = buff[i];
			if (val < 0)
				val = val * -1 + 128;
			retVal += val << 8 * i;
		}
		if (retVal < 0) {
			// System.Diagnostics.Debug.Assert(true);
		}
		return retVal;
	}

	static int BytesToInt3(byte[] buff) {
		int total = 0;
		int i;
		for (i = 0; i < buff.length; i++) {
			total += (buff[i] << 8 * i);
		}
		return total;
	}

	static class StringUtils {
		
		private static final int BYTE_RANGE = (1 + Byte.MAX_VALUE)
				- Byte.MIN_VALUE;
		private static byte[] allBytes = new byte[BYTE_RANGE];
		private static char[] byteToChars = new char[BYTE_RANGE];
		
		static {

			for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
				allBytes[i - Byte.MIN_VALUE] = (byte) i;
			}

			String allBytesString = new String(allBytes, 0, Byte.MAX_VALUE
					- Byte.MIN_VALUE);

			for (int i = 0; i < (Byte.MAX_VALUE - Byte.MIN_VALUE); i++) {
				byteToChars[i] = allBytesString.charAt(i);
			}
		}

		public static final String toAsciiString3(byte[] buffer, int startPos,
				int length) {

			char[] charArray = new char[length];
			int readpoint = startPos;

			for (int i = 0; i < length; i++) {
				charArray[i] = byteToChars[buffer[readpoint]
						- Byte.MIN_VALUE];
				readpoint++;
			}

			return new String(charArray);
		}

		
		public static final String toAsciiString(byte[] buffer) {

			return toAsciiString3(buffer, 0, buffer.length);
		}

		public static final String toAsciiString2(byte[] buffer, int startPos,
				int length) {

			return new String(buffer, startPos, length);
		}

		
		public static final String toAsciiString(byte[] buffer, int startPos,
				int length) {

			StringBuffer result = new StringBuffer();
			int endPoint = startPos + length;

			for (int i = startPos; i < endPoint; i++) {
				result.append(byteToChars[buffer[i] - Byte.MIN_VALUE]);
			}

			return result.toString();
		}
	}
}
